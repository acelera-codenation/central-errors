package br.com.central.errors.events.controller;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventLogResponse;
import br.com.central.errors.events.entity.dto.EventRequest;
import br.com.central.errors.events.entity.dto.EventResponse;
import br.com.central.errors.events.mappers.EventResponseLogMapper;
import br.com.central.errors.events.mappers.EventMapper;
import br.com.central.errors.events.service.EventService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
@Api(value = "events", produces = "application/json", consumes = "application/json")
public class EventController {

    private EventService service;
    private EventMapper mapper;
    private EventResponseLogMapper mapperLog;

    public EventController(EventService service, EventMapper mapper, EventResponseLogMapper mapperLog) {
        this.service = service;
        this.mapper = mapper;
        this.mapperLog = mapperLog;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> findAll() {
        return ResponseEntity.ok(mapper.map(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventLogResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(mapperLog.map(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EventLogResponse> save(@Valid @RequestBody EventRequest request) {

        Event event = new Event();
        event.setLevel(request.getLevel());
        event.setOrigin(request.getOrigin());
        event.setDescription(request.getDescription());
        event.setLog(request.getLog());
        event.setDate(request.getDate());
        event.setQuantity(request.getQuantity());

        Event update = service.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperLog.map(update));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable("id") Long id, @Valid @RequestBody EventRequest request) {

        Event event = service.findById(id);
        event.setLevel(request.getLevel());
        event.setOrigin(request.getOrigin());
        event.setDescription(request.getDescription());
        event.setLog(request.getLog());
        event.setDate(request.getDate());
        event.setQuantity(request.getQuantity());

        Event update = service.save(event);
        return ResponseEntity.ok(mapper.map(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        service.findById(id);
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
