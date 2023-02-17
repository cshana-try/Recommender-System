package com.movieapp.backend;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    //Find movie by movie ID
    List<Movie> findByMovieId(int id);

    //Find movie by TMDB ID
    Movie findByTmdbid(int tmdbid);

//    Movie getMovieByTitle(String title);
}


