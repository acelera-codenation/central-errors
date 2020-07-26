package br.com.central.errors.security.service;

import br.com.central.errors.security.entity.UserDetailsCustom;
import br.com.central.errors.security.repository.UserRepository;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import br.com.central.errors.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * The type User service.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(CustomTranslator.toLocale("user.not_found", username)));
        return UserDetailsCustom.build(user);
    }
}
