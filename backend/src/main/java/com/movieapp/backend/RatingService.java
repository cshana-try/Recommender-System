package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

//    public List<Rating> getRatingById(int id) {
//        return ratingRepository.findByUserId(id);
//    }

    public List<Rating> getRatingById(int id) {
        List<Rating> ratings = ratingRepository.findByUserId(id);
        return ratings.subList(0, Math.min(5, ratings.size()));
    }

    public String deleteRating(int id) {
        ratingRepository.deleteById(id);
        return "Favorites no " + id + " has been removed";
    }

//    // returns a list of all the ratings in the database.
//    public List<Rating> getRatings() {
//        return ratingRepository.findAll();
//    }
//
//    //returns a list of ratings for a particular user, given their user id.
//    public List<Rating> getRatingByUserId(int id) {
//        return ratingRepository.findByUserId(id);
//    }
//
//    //returns a rating for a particular user and movie, given their ids.
//    public Rating getUserIdAndMovieId(int userId, int movieId) {
//        return ratingRepository.findByUserIdAndMovieId(userId,movieId);
//    }
//
//    //saves a new rating to the database.
//
//
//    //returns a list of ratings for a particular movie, given its id.
//    public List<Rating> getListByMovieId(int movieId) {
//        return ratingRepository.findByMovieId(movieId);
//    }
//
//    //returns a list of all the ratings for a particular movie, given its id.
//    public List<double[]> findAllRatings(int id) {
//        return ratingRepository.findAllRatings(id);
//    }
}
