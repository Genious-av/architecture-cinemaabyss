package org.cinema.events.controller;

import lombok.RequiredArgsConstructor;

import org.cinema.events.model.*;
import org.cinema.events.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EventController {

    private final ProducerService producerService;


    @GetMapping("/api/events/health")
    public ResponseEntity<Map<String, Boolean>> health() {
        return ResponseEntity.ok(
                Map.of("status", true)
        );
    }

    @PostMapping("/api/events/movie")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponse> createMovieEvent(@RequestBody MovieEventRequest request) {
        return ResponseEntity.status(201).body(producerService.createMovieEvent(request));
    }

    @PostMapping("/api/events/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponse> createUserEvent(
            @RequestBody UserEventRequest request) {
       return ResponseEntity.status(201).body(producerService.createUserEvent(request));
    }

    @PostMapping("/api/events/payment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponse> createPaymentEvent(@RequestBody PaymentEventRequest request) {
        return ResponseEntity.status(201).body(producerService.createPaymentEvent(request));
    }
}
