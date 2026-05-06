package com.tesis.authserver.federated;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.tesis.authserver.entity.GoogleUser;
import com.tesis.authserver.entity.Role;
import com.tesis.authserver.enums.RoleName;
import com.tesis.authserver.repository.GoogleUserRepository;
import com.tesis.authserver.repository.RoleRepository;
import com.tesis.authserver.service.GlobalIdService;
import com.tesis.authserver.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
@Slf4j
public final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    private final GoogleUserRepository googleUserRepository;
    private final RoleRepository roleRepository;
    private final GlobalIdService globalIdService;

    @Override
    public void accept(OAuth2User user) {
        // Capture user in a local data store on first authentication
        String email = user.getAttribute("email"); // ✅ no user.getName()

        if (!this.googleUserRepository.findByEmail(email).isPresent()) {
            GoogleUser googleUser = GoogleUser.fromOauth2User(user);
            googleUser.setId(globalIdService.getNextId());
            Set<Role> roles = new HashSet<>();
            roles.add(getTeacherRole());
            googleUser.setRoles(roles);
            log.info(googleUser.toString());
            this.googleUserRepository.save(googleUser);
        }else{
            log.info("Bienvenido {}", user.getAttributes().get("given_name"));
        }
    }


    private Role getTeacherRole() {
        return roleRepository.findByRole(RoleName.ROLE_USER).orElse(null);
    }

}
