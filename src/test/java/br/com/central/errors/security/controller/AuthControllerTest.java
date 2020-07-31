package br.com.central.errors.security.controller;

import br.com.central.errors.App;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.suite.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/pre-sql.sql")
class AuthControllerTest extends AbstractTest {

    @Test
    void whenLoginOk() throws Exception {
        MvcResult result = signUp(user());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void whenRegisterOk() throws Exception {
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
    void whenRegisterInvalidUser() throws Exception {
        SignUp user = user();
        user.setUsername("");
        MvcResult result = signUp(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void whenRegisterInvalidEmail() throws Exception {
        SignUp user = user();
        user.setEmail("invalid");

        MvcResult result = signUp(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void whenRegisterInvalidPassword() throws Exception {
        SignUp user = user();
        user.setPassword("123");

        MvcResult result = signUp(user);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void whenNotLoggedInvalidUser() throws Exception {

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
    void whenNotLoggedBadCredentials() throws Exception {
        SignIn login = login();
        login.setUsername("credentials");
        status().is4xxClientError().match(signIn(login));
    }

    @Test
    void whenNotLogged() throws Exception {
        mvc.perform(get("/api/events"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenLoggedInEvents() throws Exception {
        SignUp user = user();
        user.setEmail("test1@teste.com");
        user.setUsername("test1");

        mvc.perform(get("/api/events")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().isOk());
    }

    @Test
    void whenNotLoggedInInvalidAccessToken() throws Exception {
        SignUp user = user();
        user.setEmail("invalidaccesstoken@teste.com");
        user.setUsername("invalidAccessToken");

        mvc.perform(get("/api/events")
                .header("Authorization", getBearerToken(user) + "invalid"))
                .andExpect(status().is4xxClientError());
    }
}
