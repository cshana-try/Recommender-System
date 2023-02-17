package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesOpService {
    @Autowired
    private MoviesOpRepository moviesOpRepository;

    public List<MoviesOp> getMovies() {
        return moviesOpRepository.findAll();
    }

    public MoviesOp getMovieById(int id) {
        return moviesOpRepository.findById(id).orElse(null);
    }

    public MoviesOp addMovie(MoviesOp moviesOp) {
        return moviesOpRepository.save(moviesOp);
    }

    public MoviesOp updateMovie(MoviesOp moviesOp) {
        return moviesOpRepository.save(moviesOp);
    }

    public void deleteMovieById(int movieId) {

        moviesOpRepository.deleteById(movieId);
    }
    public Page<MoviesOp> getmoviesNav(Pageable pageable) {
        return moviesOpRepository.findAll(pageable);
    }
}
