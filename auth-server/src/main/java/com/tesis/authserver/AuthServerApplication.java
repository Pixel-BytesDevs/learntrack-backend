package com.tesis.authserver;

import com.tesis.authserver.entity.Client;
import com.tesis.authserver.entity.Role;
import com.tesis.authserver.enums.RoleName;
import com.tesis.authserver.repository.ClientRepository;
import com.tesis.authserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

@SpringBootApplication
public class AuthServerApplication implements CommandLineRunner {


    @Autowired
    private ClientRepository clientRepository;


    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Client client = clientRepository.findByClientId("client").orElse(null);
        if (client == null) {
            return;
        }

        client.getAuthenticationMethods().clear();

        client.getAuthenticationMethods().add(
                new ClientAuthenticationMethod("client_secret_basic")
        );

        clientRepository.save(client);
    }
}
