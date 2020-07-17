package br.com.codenation.errors_center.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Load user by username user details.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
