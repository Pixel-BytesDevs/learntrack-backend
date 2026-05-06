package com.tesis.authserver.controller;

import com.tesis.authserver.dto.*;
import com.tesis.authserver.entity.AppUser;
import com.tesis.authserver.entity.RefreshToken;
import com.tesis.authserver.service.AppUserService;
import com.tesis.authserver.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AppUserService appUserService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AppUserService appUserService, RefreshTokenService refreshTokenService){
        this.appUserService = appUserService;
        this.refreshTokenService = refreshTokenService;

    }

    @PostMapping("/create")
    public ResponseEntity<UserIdCreatedResponse> createUser(@RequestBody CreateAppUserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.createUser(dto));

    }

    @PatchMapping("/complete-vark")
    public ResponseEntity<MessageDto> completeVark(@RequestParam String username) {
        return ResponseEntity.ok(appUserService.completeVark(username));
    }

    @PatchMapping("/complete-vark-google")
    public ResponseEntity<MessageDto> completeVarkGoogle(@RequestParam String email) {
        return ResponseEntity.ok(appUserService.completeVarkGoogle(email));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto dto,
                                                  HttpServletRequest request) {
        return ResponseEntity.ok(appUserService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(appUserService.refresh(request.refreshToken()));
    }
}
