package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/similarMovie")
public class SimilarMovieController {

    @Autowired
    private SimilarMovieService similarMovieService;

    // Endpoint to retrieve a list of similar movies based on the movie id
    @GetMapping("/{movieId}")
    public List<SimilarMovie> findAllMovies(@PathVariable int movieId) {
        // Calls the findAll method from SimilarMovieService and returns the result
        return similarMovieService.findAll(movieId);
    }
}
