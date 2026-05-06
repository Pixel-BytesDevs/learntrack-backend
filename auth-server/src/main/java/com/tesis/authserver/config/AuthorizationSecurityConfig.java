package com.tesis.authserver.config;



import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.tesis.authserver.entity.AppUser;
import com.tesis.authserver.entity.GoogleUser;
import com.tesis.authserver.entity.Role;
import com.tesis.authserver.federated.FederatedIdentityConfigurer;
import com.tesis.authserver.federated.UserRepositoryOAuth2UserHandler;
import com.tesis.authserver.repository.AppUserRepository;
import com.tesis.authserver.repository.GoogleUserRepository;
import com.tesis.authserver.repository.RoleRepository;
import com.tesis.authserver.service.ClientService;
import com.tesis.authserver.service.GlobalIdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Configuration
public class AuthorizationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;
    private final GoogleUserRepository googleUserRepository;
    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final GlobalIdService globalIdService;

    public AuthorizationSecurityConfig(PasswordEncoder passwordEncoder, ClientService clientService, GoogleUserRepository googleUserRepository, RoleRepository roleRepository, AppUserRepository appUserRepository, GlobalIdService globalIdService) {
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
        this.googleUserRepository = googleUserRepository;
        this.roleRepository = roleRepository;
        this.appUserRepository = appUserRepository;
        this.globalIdService = globalIdService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    // Agrega también el JwtEncoder (ya tienes jwkSource, solo faltaba el encoder)
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .securityMatcher("/oauth2/**", "/.well-known/**")
                .cors(Customizer.withDefaults());
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
        );
        http.exceptionHandling(e -> e.authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/google")
        ));
        http.with(new FederatedIdentityConfigurer(), Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain  webSecurityFilterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults());
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                    .oauth2UserHandler(new UserRepositoryOAuth2UserHandler(googleUserRepository, roleRepository, globalIdService));
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/**","/client/**", "/login", "/auth/refresh", "/create").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .with(federatedIdentityConfigurer, Customizer.withDefaults());
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/auth/**","/client/**","/create"));
        return http.build();

    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {


        return context -> {
            Authentication principal = context.getPrincipal();

            if (context.getTokenType().getValue().equals("access_token")) {

                String displayName = principal.getName();
                boolean firstLogin = false;
                Set<String> roles = new HashSet<>();
                Integer userId = null;

                // 🔵 GOOGLE USER
                boolean isGoogleUser = false;
                GoogleUser googleUser = googleUserRepository
                        .findByEmail(principal.getName())
                        .orElse(null);

                if (googleUser != null) {
                    isGoogleUser = true;

                    userId = googleUser.getId(); // 🔥 IMPORTANTE

                    displayName = googleUser.getName();
                    firstLogin = googleUser.isFirstLogin();

                    roles = googleUser.getRoles().stream()
                            .map(Role::getAuthority)
                            .collect(Collectors.toSet());
                }

                // 🟢 APP USER
                if (principal.getPrincipal() instanceof AppUser appUser) {
                    userId = appUser.getId();
                    displayName = appUser.getUsername();
                    firstLogin = appUser.isFirstLogin();

                    roles = appUser.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet());
                }

                context.getClaims()
                        .claim("id", userId)
                        .claim("roles", roles)
                        .claim("username", principal.getName())
                        .claim("name", displayName)
                        .claim("firstLogin", firstLogin)
                        .claim("isGoogleUser", isGoogleUser)
                        .claim("token_type", "access_token");
            }
        };
    }
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().issuer("http://localhost:9003").build();
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(){
        RSAKey rsaKey  = generateRSAKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    private RSAKey generateRSAKey(){
        KeyPair keyPair = generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    private KeyPair generateKeyPair(){
        KeyPair keyPair;
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }

        return keyPair;
    }





}