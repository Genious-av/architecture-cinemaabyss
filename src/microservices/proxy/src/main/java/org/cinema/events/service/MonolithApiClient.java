package org.cinema.events.service;

import lombok.RequiredArgsConstructor;
import org.cinema.events.movie.CreateMovie;
import org.cinema.events.movie.Movie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MonolithApiClient {
    private final RestClient monolithRestClient;

    public List<Movie> getAllMovies() {
        return monolithRestClient.get()
                .uri("/api/movies")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Movie getMovieById(Long id) {
        return monolithRestClient.get()
                .uri("/api/movies/{id}", id)
                .retrieve()
                .body(Movie.class);
    }

    public Movie createMovie(CreateMovie movieInput) {
        return monolithRestClient.post()
                .uri("/api/movies")
                .body(movieInput)
                .retrieve()
                .body(Movie.class);
    }
}
