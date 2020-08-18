package br.com.central.errors.events.controller;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventDTO;
import br.com.central.errors.events.entity.dto.EventLogDTO;
import br.com.central.errors.events.entity.enums.Level;
import br.com.central.errors.events.mappers.Mappers;
import br.com.central.errors.events.service.EventServiceImpl;
import br.com.central.errors.infrastructure.config.HeaderAcceptLanguage;
import br.com.central.errors.infrastructure.message.ResponseMessageError;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "events", tags = "events")
@Slf4j
@HeaderAcceptLanguage
public class EventController {

    private final EventServiceImpl service;
    private final Mappers mapEvent;

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
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = EventLogDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
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
            @ApiResponse(code = 201, message = "Success", response = EventLogDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    public ResponseEntity<EventLogDTO> save(@Validated @RequestBody EventLogDTO request) {
        Event update = service.save(mapEvent.toEvent(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapEvent.toLogDTO(update));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Success", response = EventLogDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    public ResponseEntity<EventLogDTO> update(
            @PathVariable("id") Long id,
            @Validated @RequestBody EventLogDTO request) {

        Event update = service.save(mapEvent.updateEvent(request, service.findById(id)));
        return ResponseEntity.accepted().body(mapEvent.toLogDTO(update));
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        service.findById(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "Search events ?date=2020-07-30&level=WARNING&sort=level,asc&sort=origin,asc",
            notes = "Notes: " +
                    " - Logs are hidden. \n" +
                    " - Results page you want to retrieve (0..N) \n" +
                    " - Number of records per page. \n" +
                    " - Sorting criteria format: property(,asc|desc).\n" +
                    " - Default sort order is ascending.\n" +
                    " - Multiple sort criteria are supported. \n",
            response = EventDTO.class,
            responseContainer = "Page"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = EventDTO[].class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "level",
                    dataType = "string",
                    dataTypeClass = Level.class,
                    allowableValues = "ERROR,INFO,WARNING",
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "description",
                    dataType = "string",
                    dataTypeClass = String.class,
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "log",
                    dataType = "string",
                    dataTypeClass = String.class,
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "origin",
                    dataType = "string",
                    dataTypeClass = String.class,
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "date",
                    dataType = "string",
                    dataTypeClass = LocalDate.class,
                    format = "yyyy-MM-dd",
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "quantity",
                    dataType = "integer",
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "page",
                    dataType = "integer",
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "size",
                    dataType = "integer",
                    paramType = "query"),
            @ApiImplicitParam(
                    name = "sort",
                    allowMultiple = true,
                    dataType = "string",
                    paramType = "query")})
    public Page<EventDTO> findAll(
            @QuerydslPredicate(root = Event.class) Predicate events, Pageable pageable) {
        return service.findAll(events, pageable).map(mapEvent::toDTO);
    }
}
