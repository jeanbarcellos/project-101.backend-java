package com.jeanbarcellos.project101.infra.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jeanbarcellos.project101.domain.entities.User;
import com.jeanbarcellos.project101.domain.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para obtenção do usuário pelo username (email)
 * Utiliada somente pelo Security
 */
@Service
@Slf4j
public class SecurityAuthenticationService implements UserDetailsService {

    private static final String NOT_FOUND = "Usuário não encontrado";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            log.error(NOT_FOUND + ": {}", username);
            throw new UsernameNotFoundException(NOT_FOUND);
        }

        return user;
    }

}
