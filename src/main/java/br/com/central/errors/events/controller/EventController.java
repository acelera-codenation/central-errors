package br.com.central.errors.events.controller;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventResponse;
import br.com.central.errors.events.mappers.EventMapper;
import br.com.central.errors.events.service.EventService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
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

//    @GetMapping
//    public ResponseEntity<List<EventResponse>> findAll() {
//        return ResponseEntity.ok(mapper.map(service.findAll()));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(mapper.map(service.findById(id)));
    }

    @GetMapping
    @ResponseBody
    public Iterable<Event> findAllByWebQuerydsl(
            @QuerydslPredicate(root = Event.class) Predicate predicate) {
        return service.findAll(predicate);
    }

    @PostMapping
    public ResponseEntity<EventResponse> save(@RequestBody Event event) {
        Event update = service.save(event);
        return new ResponseEntity<EventResponse>(mapper.map(update), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable("id") Long id, @RequestBody Event event) {
        Event update = service.save(event);
        return new ResponseEntity<EventResponse>(mapper.map(update), HttpStatus.OK);
    }
}
