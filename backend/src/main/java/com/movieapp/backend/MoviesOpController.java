package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moviesOp")
public class MoviesOpController {
    @Autowired
    private MoviesOpService moviesOpService;

    //retrieve all movies (limited to 400)
//    @GetMapping("/getMovies")
//    public List<MoviesOp> findAllMovies() {
//        return moviesOpService.getMovies().stream().limit(20).collect(Collectors.toList());
//    }
    @GetMapping("/getMovies")
    public List<MoviesOp> findAllMovies() {
        return moviesOpService.getMovies();
    }

    //retrieve a movie by its id
    @GetMapping("/getMovieById/{id}")
    public MoviesOp getMovieById(@PathVariable int id) {
        return moviesOpService.getMovieById(id);
    }

    //add a movie to the database
    @PostMapping("/addMovie")
    public MoviesOp addMovie(@RequestBody MoviesOp moviesOp) {
        return moviesOpService.addMovie(moviesOp);
    }

    @PutMapping("/updateMovie")
    public MoviesOp updateMovie(@RequestBody MoviesOp moviesOp) {
        return moviesOpService.updateMovie(moviesOp);
    }

    @DeleteMapping("/deleteMovieById/{movieId}")
    public void deleteMovieById(@PathVariable int movieId) {
        moviesOpService.deleteMovieById(movieId);
    }

    @GetMapping("/moviesNav")
    public Page<MoviesOp> moviesNav(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "15") int size) {
        return moviesOpService.getmoviesNav(PageRequest.of(page, size));
    }

}
