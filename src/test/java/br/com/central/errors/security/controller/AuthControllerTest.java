package br.com.central.errors.security.controller;

import br.com.central.errors.security.entity.dto.JwtResponse;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.security.repository.UserRepository;
import br.com.central.errors.security.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    private SignUp user;
    private SignIn login;

    @BeforeEach
    void setUp() {
        this.user = new SignUp();
        user.setUsername("adm");
        user.setEmail("samuel@teste.com");
        user.setPassword("1234556");

        this.login = new SignIn();
        login.setUsername(user.getUsername());
        login.setPassword(user.getPassword());
    }

    @Test
    void signUp() throws Exception {
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void signUpBadRequestInvalidUserName() throws Exception {

        user.setUsername("");
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpBadRequestInvalidEmail() throws Exception {

        user.setEmail("invalido");
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

        user.setUsername("adm");
        user.setEmail("samuel@teste.com");
        user.setPassword("123");
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpBadRequestInvalidPassword() throws Exception {
        user.setPassword("123");
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signIn() throws Exception {

        user.setEmail("teste@teste.com");
        user.setUsername("login");
        login.setUsername(user.getUsername());
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }

    @Test
    void signInInvalidUser() throws Exception {
        user.setEmail("testeerror@teste.com");
        user.setUsername("loginerror");
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        login.setUsername("");
        mockMvc.perform(post("/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void signInBadCredentials() throws Exception {
        login.setUsername("badcredentials");
        mockMvc.perform(post("/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void invalidAccess() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void validAccess() throws Exception {
        user.setEmail("teste1@teste.com");
        user.setUsername("teste1");
        String accessToken = obtainAccessToken(user);
        mockMvc.perform(get("/api/events")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    void invalidAccessToken() throws Exception {
        user.setEmail("invalidAccessToken@teste.com");
        user.setUsername("invalidAccessToken");
        String accessToken = obtainAccessToken(user);
        mockMvc.perform(get("/api/events")
                .header("Authorization", "Bearer " + accessToken + "invalid"))
                .andExpect(status().is4xxClientError());
    }

    private String obtainAccessToken(SignUp userToken) throws Exception {

        login.setUsername(userToken.getUsername());
        mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToken)))
                .andExpect(status().isOk());


        MvcResult result = mockMvc.perform(post("/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwt = objectMapper.readValue(contentAsString, JwtResponse.class);

        return jwt.getToken();
    }


}