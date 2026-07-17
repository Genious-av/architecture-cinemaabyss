package org.cinema.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class UserEventRequest {

    @JsonProperty("user_id")
    private Long userId;

    private String username;

    private String email;

    private String action;

    private Instant timestamp;
}
