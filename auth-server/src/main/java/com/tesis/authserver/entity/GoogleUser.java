package com.tesis.authserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GoogleUser {
    @Id
    private int id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String pictureUrl;
    private boolean firstLogin = false;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "google_user_role", joinColumns = @JoinColumn(name = "google_user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;




    public static GoogleUser fromOauth2User(OAuth2User user){
        // ✅ Leer email del atributo, no de getName()
        String email = user.getAttribute("email");

        return GoogleUser.builder()
                .email(email)
                .name(user.getAttribute("name"))
                .givenName(user.getAttribute("given_name"))
                .familyName(user.getAttribute("family_name"))
                .pictureUrl(user.getAttribute("picture"))
                .build();

    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
