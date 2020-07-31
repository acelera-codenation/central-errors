package br.com.central.errors.security.service.interfaces;


import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.dto.JwtResponse;

public interface UserInterface {

    <S extends User> S save(S user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    JwtResponse login(Object principal, Object credentials);

}
