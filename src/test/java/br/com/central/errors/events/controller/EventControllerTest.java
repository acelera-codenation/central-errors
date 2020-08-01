package br.com.central.errors.events.controller;

import br.com.central.errors.App;
import br.com.central.errors.events.entity.Level;
import br.com.central.errors.events.entity.dto.EventLogResponse;
import br.com.central.errors.events.entity.dto.EventRequest;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.suite.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/pre-sql.sql")
class EventControllerTest extends AbstractTest {

    private EventRequest getEvent() {
        EventRequest request = new EventRequest();
        request.setDate(LocalDateTime.now());
        request.setDescription("Teste");
        request.setLevel(Level.ERROR);
        request.setLog("Log teste whenSaveEvent");
        request.setQuantity(1);
        request.setOrigin("Testes");
        return request;
    }

    private String getAuthToken() throws Exception {
        SignUp user = user();
        user.setEmail("usertest@teste.com");
        user.setUsername("usertest");
        return getBearerToken(user);
    }

    private EventLogResponse getContentEventLogResponse(MvcResult result) throws UnsupportedEncodingException, com.fasterxml.jackson.core.JsonProcessingException {
        String response = result.getResponse().getContentAsString();
        return JsonToMap(response, EventLogResponse.class);
    }

    @Test
    void whenFindAllEvents() throws Exception {
        mvc.perform(get("/api/events")
                .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindEventById() throws Exception {
        ResultActions result = mvc.perform(get("/api/events/1")
                .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());

        assertNotNull(getContentEventLogResponse(result.andReturn()));
    }

    @Test
    void whenFindByIdReturnError() throws Exception {
        mvc.perform(get("/api/events/3")
                .header("Authorization", getAuthToken()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenSaveEvent() throws Exception {
        MvcResult result = mvc.perform(post("/api/events").content(mapToJson(getEvent()))
                .header("Authorization", getAuthToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        EventLogResponse eventSaved = getContentEventLogResponse(result);

        assertNotNull(eventSaved.getId());
    }

    @Test
    void whenUpdateEvent() throws Exception {
        String token = getAuthToken();

        EventRequest request = getEvent();

        MvcResult result = mvc.perform(post("/api/events")
                .content(mapToJson(request))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        EventLogResponse eventSaved = getContentEventLogResponse(result);
        assertNotNull(eventSaved.getId());
        eventSaved.setLevel(Level.WARNING);

        request.setDescription("Changes");

        mvc.perform(patch("/api/events/" + eventSaved.getId())
                .content(mapToJson(request))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void whenDeleteEvent() throws Exception {
        String token = getAuthToken();

        MvcResult result = mvc.perform(post("/api/events").content(mapToJson(getEvent()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        EventLogResponse eventSaved = getContentEventLogResponse(result);

        assertNotNull(eventSaved.getId());

        mvc.perform(delete("/api/events/" + eventSaved.getId())
                .header("Authorization", token))
                .andExpect(status().isAccepted());
    }

    @Test
    void whenDeleteEventNotFound() throws Exception {
        String token = getAuthToken();

        mvc.perform(delete("/api/events/-1")
                .header("Authorization", token))
                .andExpect(status().isNotFound());
    }
}