package br.com.codenation.errors_center.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The type User.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20)
    private String username;

    @NotBlank
    @Email
    @Column(length = 40)
    private String email;

    @NotBlank
    private String password;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     */
    public User() {
    }
}
