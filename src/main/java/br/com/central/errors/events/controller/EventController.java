package br.com.central.errors.events.controller;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventLogResponse;
import br.com.central.errors.events.entity.dto.EventResponse;
import br.com.central.errors.events.mappers.EventLogMapper;
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
    private EventLogMapper mapperLog;

    public EventController(EventService service, EventMapper mapper, EventLogMapper mapperLog) {
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
    public ResponseEntity<EventResponse> save(@RequestBody Event event) {
        Event update = service.save(event);
        return new ResponseEntity<EventResponse>(mapper.map(update), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable("id") Long id, @Valid @RequestBody Event event) {
        Event update = service.save(event);
        return new ResponseEntity<EventResponse>(mapper.map(update), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (service.findById(id) != null) {
            service.delete(id);
            return ResponseEntity.accepted().build();
        } else return ResponseEntity.notFound().build();
    }
}
