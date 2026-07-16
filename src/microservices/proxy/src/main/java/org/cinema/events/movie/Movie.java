package org.cinema.events.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Movie {

    private Long id;

    private String title;

    private String description;

    private List<String> genres;

    private Double rating;
}
