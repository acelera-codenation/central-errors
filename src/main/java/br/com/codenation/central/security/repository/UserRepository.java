package br.com.codenation.central.security.repository;

import br.com.codenation.central.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Exists by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    boolean existsByUsername(String username);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);
}
