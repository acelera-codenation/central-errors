package br.com.central.errors.suite;

import br.com.central.errors.security.entity.dto.JwtResponse;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractTest {

    public static final String USERNAME = "adm";
    public static final String PASSWORD = "1234556";
    public static final String EMAIL = "samuel@teste.com";
    public static final String AUTH_SIGNIN = "/auth/signin";
    public static final String AUTH_SIGNUP = "/auth/signup";

    @Autowired
    protected MockMvc mvc;

    protected void setUp() {
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T JsonToMap(String content, Class<T> valueType) throws JsonProcessingException, JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, valueType);
    }

    protected SignUp user() {

        SignUp user = new SignUp();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        return user;
    }

    protected SignIn login() {
        SignIn login = new SignIn();
        login.setUsername(USERNAME);
        login.setPassword(PASSWORD);
        return login;
    }

    protected String getBearerToken(SignUp user) throws Exception {

        SignIn login = login();
        login.setUsername(user.getUsername());

        status().isOk().match(signUp(user));

        MvcResult result = signIn(login);
        status().isOk().match(result);

        String content = result.getResponse().getContentAsString();
        JwtResponse jwt = JsonToMap(content, JwtResponse.class);

        return "Bearer " + jwt.getToken();
    }

    protected MvcResult signIn(SignIn login) throws Exception {
        return mvc.perform(post(AUTH_SIGNIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(login))).andReturn();
    }

    protected MvcResult signUp(SignUp user) throws Exception {
        return mvc.perform(post(AUTH_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user))).andReturn();
    }

}
