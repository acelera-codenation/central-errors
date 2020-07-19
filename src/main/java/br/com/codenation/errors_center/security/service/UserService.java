package br.com.codenation.errors_center.security.service;

import br.com.codenation.errors_center.infrastructure.translate.CustomTranslator;
import br.com.codenation.errors_center.security.entity.User;
import br.com.codenation.errors_center.security.entity.UserDetailsCustom;
import br.com.codenation.errors_center.security.repository.UserRepository;
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
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(CustomTranslator.toLocale("user.not_found", username)));
        return UserDetailsCustom.build(user);
    }
}
