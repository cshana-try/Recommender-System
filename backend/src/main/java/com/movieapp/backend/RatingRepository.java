package com.movieapp.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    // find all ratings for a specific user
    List<Rating> findByUserId(int id);

//    // find all ratings in the table
//    @Query("SELECT rating FROM rating")
//    List<double[]> findAllRatings(int id);
//
//    // find a rating by userId and movieId
//    Rating findByUserIdAndMovieId(int userId, int movieId);
//
//    // find all ratings for a specific movie
//    List<Rating> findByMovieId(int id);


}
