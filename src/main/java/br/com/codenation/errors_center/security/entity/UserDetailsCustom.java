package br.com.codenation.errors_center.security.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * The type User details custom.
 */
@Getter
@RequiredArgsConstructor
public class UserDetailsCustom implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private UserDetailsCustom(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Build user details custom.
     *
     * @param user the user
     * @return the user details custom
     */
    public static UserDetailsCustom build(User user) {
        return new UserDetailsCustom(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
