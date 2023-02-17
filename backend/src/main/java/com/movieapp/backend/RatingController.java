package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/addRating")
    public Rating addRating(@RequestBody Rating rating) {
        // save the incoming rating object to the database
        return ratingService.saveRating(rating);
    }

    @GetMapping("/getRatings/{id}")
    public List<Rating> FindAllRatingsById(@PathVariable int id) {
        return ratingService.getRatingById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFavorite(@PathVariable int id) {
        return ratingService.deleteRating(id);
    }

//    // Find ratings by userId endpoint
//    @GetMapping("findRatingByUserId/{id}")
//    public List<Rating> findRatingByUserId (@PathVariable int id) {
//        // get a list of ratings based on the userId
//        return ratingService.getRatingByUserId(id);
//    }
//
//    // Get all ratings endpoint
//    @GetMapping("/getRatings")
//    public List<Rating> getRatings() {
//        // get a list of all ratings
//        return ratingService.getRatings();
//    }
//
//    // Find rating by userId and movieId endpoint
//    @GetMapping("/findByUserIdAndMovieId/{userId}/{movieId}")
//    public Rating findByUserIdAndMovieId(@PathVariable("userId") int userId, @PathVariable("movieId") int movieId) {
//        // get the rating based on the userId and movieId
//        return ratingService.getUserIdAndMovieId(userId,movieId);
//    }
//
//    // Find ratings by movieId endpoint
//    @GetMapping("/findByMovieId")
//    public List<Rating> findByMovieId(@PathVariable int movieId) {
//        // get a list of ratings based on the movieId
//        return ratingService.getListByMovieId(movieId);
//    }
//
//    // Find all ratings endpoint
//    @GetMapping("/allRatings")
//    public List<double[]> findAllRatings(@PathVariable int id) {
//        // get a list of all ratings
//        return ratingService.findAllRatings(id);
//    }

}
