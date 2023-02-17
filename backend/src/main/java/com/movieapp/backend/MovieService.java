package com.movieapp.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesById(int id) {
        return movieRepository.findByMovieId(id);
    }

    public Movie getMovieByTmdbid(int tmdbid) {
        return movieRepository.findByTmdbid(tmdbid);
    }

    public Page<Movie> getPaginatedMovies(int pageNo, int pageSize, String sortBy) {
        return movieRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
    }

//    public Movie findByTitle(String title) {
//        return movieRepository.getMovieByTitle(title);
//    }
}

