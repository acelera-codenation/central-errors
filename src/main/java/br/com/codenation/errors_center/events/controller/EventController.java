package br.com.codenation.errors_center.events.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/events")
@Api(value = "events", produces = "application/json", consumes = "application/json")
public class EventController {

    public ResponseEntity<String> get() {
        return ResponseEntity.ok().body("TODO");
    }

}
