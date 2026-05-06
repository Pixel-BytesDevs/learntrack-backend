package com.tesis.authserver.service;

import com.tesis.authserver.dto.CreateClientDto;
import com.tesis.authserver.dto.MessageDto;
import com.tesis.authserver.entity.Client;
import com.tesis.authserver.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements RegisteredClientRepository {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(RegisteredClient registeredClient) {


    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(() -> new RuntimeException("client not found"));
        return Client.toRegisteredClient(client);
    }

    public MessageDto create(CreateClientDto dto){
        Client client = clientFromDto(dto);
        clientRepository.save(client);
        return new MessageDto("client" + client.getClientId() + " saved");
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("client not found"));
        return Client.toRegisteredClient(client);
    }

    private Client clientFromDto(CreateClientDto dto) {

        Set<ClientAuthenticationMethod> authMethods = dto.getAuthenticationMethods()
                .stream()
                .map(method -> switch (method) {
                    case "client_secret_basic" -> ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
                    case "client_secret_post" -> ClientAuthenticationMethod.CLIENT_SECRET_POST;
                    case "none" -> ClientAuthenticationMethod.NONE;
                    default -> new ClientAuthenticationMethod(method);
                })
                .collect(Collectors.toSet());

        Set<AuthorizationGrantType> grantTypes = dto.getAuthorizationGrantTypes()
                .stream()
                .map(type -> switch (type) {
                    case "authorization_code" -> AuthorizationGrantType.AUTHORIZATION_CODE;
                    case "refresh_token" -> AuthorizationGrantType.REFRESH_TOKEN;
                    case "client_credentials" -> AuthorizationGrantType.CLIENT_CREDENTIALS;
                    default -> new AuthorizationGrantType(type);
                })
                .collect(Collectors.toSet());

        return Client.builder()
                .clientId(dto.getClientId())
                .clientSecret(passwordEncoder.encode(dto.getClientSecret()))
                .authenticationMethods(authMethods)
                .authorizationGrantTypes(grantTypes)
                .redirectUris(dto.getRedirectUris())
                .scopes(dto.getScopes())
                .requireProofKey(dto.isRequireProofKey())
                .build();
    }


}
