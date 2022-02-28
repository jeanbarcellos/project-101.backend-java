package com.jeanbarcellos.demo.config;

import com.jeanbarcellos.demo.domain.entities.User;
import com.jeanbarcellos.demo.domain.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para obtenção do usuário pelo username (email)
 * Utiliada somente pelo Security
 */
@Service
@Slf4j
public class SecurityAuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            log.error("Usuário não encontrado: {}", username);
            throw new UsernameNotFoundException("Usuário não enconrado");
        }

        log.info("Usuário encontrado: {}", username);

        return user;
    }

}
