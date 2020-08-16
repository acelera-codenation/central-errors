package br.com.central.errors.security.controller;

import br.com.central.errors.security.entity.dto.ResetPassword;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.suite.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends AbstractTest {

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
    void whenResetPasswordOk() throws Exception {
        SignUp user = user();
        user.setEmail("whenresetpasswordok@teste.com");
        user.setUsername("whenResetPasswordOk");

        String token = getBearerToken(user);

        ResetPassword newAccess = new ResetPassword();
        newAccess.setUsername("whenResetPasswordOk");
        newAccess.setPassword(PASSWORD);
        newAccess.setNewPassword("654321");
        newAccess.setConfirmPassword("654321");

        MvcResult result = mvc.perform(post(USER_RESET_PASSWORD).content(mapToJson(newAccess))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void whenResetPasswordDontMatch() throws Exception {
        SignUp user = user();
        user.setEmail("whenresetpasswordok@teste.com");
        user.setUsername("whenResetPasswordOk");

        String token = getBearerToken(user);

        ResetPassword newAccess = new ResetPassword();
        newAccess.setUsername("whenResetPasswordOk");
        newAccess.setPassword(PASSWORD);
        newAccess.setNewPassword("654321");
        newAccess.setConfirmPassword("11111");

        MvcResult result = mvc.perform(post(USER_RESET_PASSWORD).content(mapToJson(newAccess))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenResetPasswordActualDontMatch() throws Exception {
        SignUp user = user();
        user.setEmail("whenresetpasswordok@teste.com");
        user.setUsername("whenResetPasswordOk");

        String token = getBearerToken(user);

        ResetPassword newAccess = new ResetPassword();
        newAccess.setUsername("whenResetPasswordOk");
        newAccess.setPassword("notmatch");
        newAccess.setNewPassword("654321");
        newAccess.setConfirmPassword("654321");

        MvcResult result = mvc.perform(post(USER_RESET_PASSWORD).content(mapToJson(newAccess))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenResetPasswordUserNotFound() throws Exception {
        SignUp user = user();
        user.setEmail("whenresetpasswordok@teste.com");
        user.setUsername("whenResetPasswordOk");

        String token = getBearerToken(user);

        ResetPassword newAccess = new ResetPassword();
        newAccess.setUsername("whenResetPasswordOk");
        newAccess.setPassword("notmatch");
        newAccess.setNewPassword("654321");
        newAccess.setConfirmPassword("654321");

        MvcResult result = mvc.perform(post(USER_RESET_PASSWORD).content(mapToJson(newAccess))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
    }


}
