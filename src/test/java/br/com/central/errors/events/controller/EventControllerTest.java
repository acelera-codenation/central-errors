package br.com.central.errors.events.controller;

import br.com.central.errors.App;
import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.Level;
import br.com.central.errors.events.entity.dto.EventLogResponse;
import br.com.central.errors.events.entity.dto.EventResponse;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.suite.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/pre-sql.sql")
class EventControllerTest extends AbstractTest {

    private Event getEvent() {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setDescription("Teste");
        event.setLevel(Level.ERROR);
        event.setLog("Log teste whenSaveEvent");
        event.setQuantity(1);
        event.setOrigin("Testes");
        return event;
    }

    @Test
    void whenFindAllEvents() throws Exception {
        SignUp user = user();
        user.setEmail("findallok@teste.com");
        user.setUsername("findallok");

        mvc.perform(get("/api/events")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindEventById() throws Exception {
        SignUp user = user();
        user.setEmail("findallok@teste.com");
        user.setUsername("findallok");

        ResultActions result = mvc.perform(get("/api/events/1")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().isOk());

        String content = result.andReturn().getResponse().getContentAsString();
        EventLogResponse event = JsonToMap(content, EventLogResponse.class);

        assertTrue(event instanceof EventLogResponse);
    }

    @Test
    void whenFindByIdReturnError() throws Exception {

        SignUp user = user();
        user.setEmail("findoneerror@teste.com");
        user.setUsername("findoneerror");

        mvc.perform(get("/api/events/3")
                .header("Authorization", getBearerToken(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenSaveEvent() throws Exception {
        SignUp user = user();
        user.setEmail("whenSaveEvent@teste.com");
        user.setUsername("whenSaveEvent");

        MvcResult result = mvc.perform(post("/api/events").content(mapToJson(getEvent()))
                .header("Authorization", getBearerToken(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String response = result.getResponse().getContentAsString();
        EventResponse eventSaved = JsonToMap(response, EventResponse.class);

        assertNotNull(eventSaved.getId());
    }

    @Test
    void whenDeleteEvent() throws Exception {
        SignUp user = user();
        user.setEmail("whenDeleteEvent@teste.com");
        user.setUsername("whenDeleteEvent");

        String token = getBearerToken(user);

        MvcResult result = mvc.perform(post("/api/events").content(mapToJson(getEvent()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String response = result.getResponse().getContentAsString();
        EventResponse eventSaved = JsonToMap(response, EventResponse.class);

        assertNotNull(eventSaved.getId());

        mvc.perform(delete("/api/events/" + eventSaved.getId())
                .header("Authorization", token))
                .andExpect(status().isAccepted());
    }
}