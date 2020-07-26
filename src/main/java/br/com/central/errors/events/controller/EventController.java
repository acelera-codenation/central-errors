package br.com.central.errors.events.controller;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventResponse;
import br.com.central.errors.events.mappers.EventMapper;
import br.com.central.errors.events.service.EventService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
@Api(value = "events", produces = "application/json", consumes = "application/json")
public class EventController {

    private EventService service;
    private EventMapper mapper;

    public EventController(EventService service, EventMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> findAll() {
        return ResponseEntity.ok(mapper.map(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(mapper.map(service.findById(id)));
    }



}
