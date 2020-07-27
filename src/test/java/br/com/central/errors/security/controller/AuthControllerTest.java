package br.com.central.errors.security.controller;

import br.com.central.errors.App;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.security.repository.UserRepository;
import br.com.central.errors.security.service.JwtService;
import br.com.central.errors.suite.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/pre-sql.sql")
class AuthControllerTest extends AbstractTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Test
    void signInOk() throws Exception {
        MvcResult result = signUp(user());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void signUpOK() throws Exception {
        SignUp user = user();
        SignIn login = login();

        user.setUsername("signUpOk");
        user.setEmail("signupok@teste.com");
        login.setUsername("signUpOk");

        MvcResult result = signUp(user);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        result = signIn(login);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void signUpBadRequestInvalidUserName() throws Exception {
        SignUp user = user();
        user.setUsername("");
        MvcResult result = signUp(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void signUpBadRequestInvalidEmail() throws Exception {
        SignUp user = user();
        user.setEmail("invalido");

        MvcResult result = signUp(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void signUpBadRequestInvalidPassword() throws Exception {
        SignUp user = user();
        user.setPassword("123");

        MvcResult result = signUp(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void signInInvalidUser() throws Exception {

        SignUp user = user();
        SignIn login = login();

        user.setEmail("signInInvalidUser@teste.com");
        user.setUsername("signInInvalidUser");
        login.setUsername("");

        MvcResult result = signUp(user);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        result = signIn(login);
        status().is4xxClientError().match(result);
    }

    @Test
    void signInBadCredentials() throws Exception {
        SignIn login = login();
        login.setUsername("badcredentials");
        status().is4xxClientError().match(signIn(login));
    }

    @Test
    void invalidAccess() throws Exception {
        mvc.perform(get("/api/events"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void validAccess() throws Exception {
        SignUp user = user();
        user.setEmail("teste1@teste.com");
        user.setUsername("teste1");

        mvc.perform(get("/api/events")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().isOk());
    }

    @Test
    void invalidAccessToken() throws Exception {
        SignUp user = user();
        user.setEmail("invalidaccesstoken@teste.com");
        user.setUsername("invalidAccessToken");

        mvc.perform(get("/api/events")
                .header("Authorization", getBearerToken(user) + "invalid"))
                .andExpect(status().is4xxClientError());
    }
}
