package org.cinema.events.controller;

import lombok.RequiredArgsConstructor;

import org.cinema.events.movie.CreateMovie;
import org.cinema.events.movie.Movie;
import org.cinema.events.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @GetMapping()
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody CreateMovie createMovie) {
        return movieService.createMovie(createMovie);
    }
}
