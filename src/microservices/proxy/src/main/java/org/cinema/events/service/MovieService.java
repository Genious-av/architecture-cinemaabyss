package org.cinema.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cinema.events.movie.CreateMovie;
import org.cinema.events.movie.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    @Value("${MONOLITH_URL}")
    private String monolithUrl;

    @Value("${MOVIES_SERVICE_URL}")
    private String moviesServiceUrl;

    @Value("${GRADUAL_MIGRATION}")
    private Boolean gradualMigration;

    @Value("${MOVIES_MIGRATION_PERCENT}")
    private String migrationPercent;

    private final MovieApiClient movieApiClient;
    private final MonolithApiClient monolithApiClient;

    public List<Movie> getAllMovies() {
        if (useMonolith()) {
            log.info("Requesting monolith service");
            return monolithApiClient.getAllMovies();
        }
        log.info("Requesting movie service");
        return movieApiClient.getAllMovies();
    }

    public Movie getMovieById(Long id) {
        if (useMonolith()) {
            log.info("Requesting monolith service");
            return monolithApiClient.getMovieById(id);
        }
        log.info("Requesting movie service");
        return movieApiClient.getMovieById(id);
    }

    public Movie createMovie(CreateMovie createMovie) {
        if (useMonolith()) {
            log.info("Requesting monolith service");
            return monolithApiClient.createMovie(createMovie);
        }
        log.info("Requesting movie service");
        return movieApiClient.createMovie(createMovie);
    }

    private boolean useMonolith() {
        Integer percent = Integer.valueOf(migrationPercent);
        if (gradualMigration == false) {
           return true;
        } else  {
            return ThreadLocalRandom.current().nextInt(100) > percent;
        }
    }

}
