package com.tesis.authserver.service;

import com.tesis.authserver.dto.*;
import com.tesis.authserver.entity.AppUser;
import com.tesis.authserver.entity.GoogleUser;
import com.tesis.authserver.entity.RefreshToken;
import com.tesis.authserver.entity.Role;
import com.tesis.authserver.enums.RoleName;
import com.tesis.authserver.repository.AppUserRepository;
import com.tesis.authserver.repository.GlobalIdRepository;
import com.tesis.authserver.repository.GoogleUserRepository;
import com.tesis.authserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final GoogleUserRepository googleUserRepository;
    private final RefreshTokenService refreshTokenService;
    private final GlobalIdService globalIdService;

    public TokenResponseDto login(LoginRequestDto dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        AppUser user = (AppUser) authentication.getPrincipal();

        String accessToken = generateAccessToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new TokenResponseDto(accessToken, refreshToken.getToken());
    }

    private String generateAccessToken(AppUser user) {

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://3.145.22.173:9003")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES)) // 🔥 AJUSTA AQUÍ
                .subject(user.getUsername())
                .claim("id",user.getId())
                .claim("roles", roles)
                .claim("username", user.getUsername())
                .claim("firstLogin", user.isFirstLogin()) // 🔥 clave
                .claim("token_type", "access_token")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public TokenResponseDto refresh(String refreshToken) {
        RefreshToken token = refreshTokenService.verifyToken(refreshToken);

        AppUser user = token.getUser();

        String newAccessToken = generateAccessToken(user);

        return new TokenResponseDto(newAccessToken, refreshToken);
    }

    public MessageDto completeVarkGoogle(String email) {
        GoogleUser user = googleUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Google user not found"));
        user.setFirstLogin(true);
        googleUserRepository.save(user);
        return new MessageDto("VARK completado para " + email);
    }

    public UserIdCreatedResponse createUser(CreateAppUserDto dto){

        if(appUserRepository.findByUsername(dto.username()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuario ya existe"
            );
        }

        AppUser appUser = AppUser.builder()
                .id(globalIdService.getNextId())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        log.info("pasando por aca");
        dto.roles().forEach(r -> {
            Role role = repository.findByRole(RoleName.valueOf(r))
                    .orElseThrow(() -> new RuntimeException("role not found"));
            roles.add(role);
        });
       // roles.add(getStudentRole());

        log.info("pasando por aca");
        appUser.setRoles(roles);
        appUserRepository.save(appUser);
        return new UserIdCreatedResponse(appUser.getId());
    }

    public MessageDto completeVark(String username) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
        user.setFirstLogin(true);
        appUserRepository.save(user);
        return new MessageDto("VARK completado para " + username);
    }


    private Role getTeacherRole() {
       return repository.findByRole(RoleName.ROLE_PROFESOR).orElse(null);
    }

    private Role getStudentRole() {
        return repository.findByRole(RoleName.ROLE_USER).orElse(null);
    }
}
