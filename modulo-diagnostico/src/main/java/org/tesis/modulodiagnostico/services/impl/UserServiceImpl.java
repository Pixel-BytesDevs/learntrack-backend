package org.tesis.modulodiagnostico.services.impl;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.exceptions.UserNotFoundException;

@Service
public class UserServiceImpl {

   /* private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }
    public Usuario findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }*/
}
