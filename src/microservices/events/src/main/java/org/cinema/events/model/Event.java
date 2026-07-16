package org.cinema.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Event {
    private long id;
    private String type;
    private Instant timestamp;
    private String payload;
}
