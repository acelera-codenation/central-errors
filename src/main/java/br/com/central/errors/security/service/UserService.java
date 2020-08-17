package br.com.central.errors.security.service;

import br.com.central.errors.infrastructure.translate.CustomTranslator;
import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.UserDetailsCustom;
import br.com.central.errors.security.entity.dto.AccessToken;
import br.com.central.errors.security.entity.dto.ResetPassword;
import br.com.central.errors.security.exceptions.PasswordMatchException;
import br.com.central.errors.security.repository.UserRepository;
import br.com.central.errors.security.service.interfaces.UserInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService, UserInterface {

    private UserRepository repository;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    private PasswordEncoder encoder;

    public UserService(UserRepository repository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder encoder) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(CustomTranslator.toLocale("user.not_found", username)));
        return UserDetailsCustom.build(user);
    }

    @Override
    public <S extends User> S save(S user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public AccessToken login(Object principal, Object credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(principal, credentials));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        return new AccessToken(jwt,
                userDetailsCustom.getId(),
                userDetailsCustom.getUsername(),
                userDetailsCustom.getEmail());

    }

    @Override
    public void changeUserPassword(ResetPassword account) {

        if (account.getNewPassword().compareTo(account.getConfirmPassword()) != 0) {
            throw new PasswordMatchException("user.password_wrong");
        }

        User user = repository.findByUsername(account.getUsername()).orElseThrow(ResourceNotFoundException::new);

        if (!encoder.matches(account.getPassword(), user.getPassword()))
            throw new PasswordMatchException("user.password_wrong");

        user.setPassword(encoder.encode(account.getNewPassword()));

        repository.save(user);
    }

}
