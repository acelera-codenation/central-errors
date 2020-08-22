package br.com.central.errors.security.service.interfaces;


import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.dto.AccessToken;
import br.com.central.errors.security.entity.dto.ResetPassword;

public interface UserInterface {

    <S extends User> S save(S user);

    AccessToken login(Object principal, Object credentials);

    void changeUserPassword(final ResetPassword user);

}
