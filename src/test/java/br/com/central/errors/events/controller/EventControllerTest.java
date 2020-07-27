package br.com.central.errors.events.controller;

import br.com.central.errors.App;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.suite.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/pre-sql.sql")
class EventControllerTest extends AbstractTest {

    @Test
    void findAllOk() throws Exception {
        SignUp user = user();
        user.setEmail("findallok@teste.com");
        user.setUsername("findallok");

        mvc.perform(get("/api/events")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdError() throws Exception {

        SignUp user = user();
        user.setEmail("findoneerror@teste.com");
        user.setUsername("findoneerror");

        mvc.perform(get("/api/events/3")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().is4xxClientError());
    }
}