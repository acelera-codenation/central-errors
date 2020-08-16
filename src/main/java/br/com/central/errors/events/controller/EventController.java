package br.com.central.errors.events.controller;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventDTO;
import br.com.central.errors.events.entity.dto.EventLogDTO;
import br.com.central.errors.events.entity.enums.Level;
import br.com.central.errors.events.mappers.Mappers;
import br.com.central.errors.events.service.EventServiceImpl;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "events")
@ApiResponses({
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class EventController {

    private EventServiceImpl service;
    private Mappers mapEvent;

    public EventController(EventServiceImpl service, Mappers mapEvent) {
        this.service = service;
        this.mapEvent = mapEvent;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "Find event by {id}",
            notes = "All event information was returned",
            response = EventLogDTO.class)
    @ApiResponse(code = 200, message = "Success")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization", value = "Bearer token",
                    required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<EventLogDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(mapEvent.toLogDTO(service.findById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            value = "Save event",
            response = EventLogDTO.class)
    @ApiResponses({
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<EventLogDTO> save(@Valid @RequestBody EventLogDTO request) {
        Event update = service.save(mapEvent.toEvent(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapEvent.toLogDTO(update));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            value = "Update event by {id}",
            response = EventLogDTO.class)
    public ResponseEntity<EventLogDTO> update(
            @PathVariable("id") Long id,
            @RequestBody EventLogDTO request) {

        Event update = service.save(mapEvent.updateEvent(request, service.findById(id)));
        return ResponseEntity.accepted().body(mapEvent.toLogDTO(update));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(
            value = "Delete event by {id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        service.findById(id);
        service.delete(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    @ApiOperation(
            value = "Search events",
            notes = "Search events by: Level, Description, log, Origin, Date and Quantity. Note: At this endpoint you" +
                    " can't see log properties.",
            response = EventDTO.class,
            responseContainer = "Page")
    public Page<EventDTO> findAll(
            @QuerydslPredicate(root = Event.class) Predicate events,
            @RequestParam(required = false) Level level,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String log,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDate date,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date,
            @RequestParam(required = false) String quantity,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(events, pageable).map(mapEvent::toDTO);
    }
}
