package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //retrieve all movies (limited to 400)
    @GetMapping("/getMovies")
    public List<Movie> findAllMovies() {
        return movieService.getMovies().stream().limit(15).collect(Collectors.toList());
    }

    //retrieve a movie by its id
    @GetMapping("/getMovieById/{id}")
    public Movie getMovieById(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

    //to retrieve a movie by its tmdbid
    @GetMapping("/findByTmdbid/{id}")
    public Movie getByTmdbid(@PathVariable int id) {
        return movieService.getMovieByTmdbid(id);
    }

    //retrieve multiple movies by their id
    @GetMapping("/getMoviesById/{id}")
    public List<Movie> getMoviesById(@PathVariable int id) {
        return movieService.getMoviesById(id);
    }

    @GetMapping("/pageNav")
    public Page<Movie> getPaginatedMovies(@RequestParam(defaultValue = "0") int pageNo,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(defaultValue = "movieId") String sortBy) {
        return movieService.getPaginatedMovies(pageNo, pageSize, sortBy);
    }

//    @GetMapping("/getMovieByTitle")
//    public Movie getMovieByTitle(@PathVariable String title) {
//        return movieService.findByTitle(title);
//    }
}
