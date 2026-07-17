package org.cinema.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventResponse {
    private String status;
    private int partition;
    private long offset;
    private Event event;
}
