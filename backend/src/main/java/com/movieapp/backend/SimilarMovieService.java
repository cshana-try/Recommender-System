package com.movieapp.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SimilarMovieService {
    private final SimilarMovieRepository similarMovieRepository;

    // constructor that sets the similarMovieRepository
    public SimilarMovieService(SimilarMovieRepository similarMovieRepository) {
        this.similarMovieRepository = similarMovieRepository;
    }

    // finds all the similar movies based on the movie id
    public List<SimilarMovie> findAll(int movieId) {
        return similarMovieRepository.findAll(movieId);
    }

// code block below is commented out and does not currently execute
// // JDBC driver name and database URL
// static final String JDBC_DRIVER = "com.ibm.db2.jcc.DB2Driver";
// static final String DB_URL ="jdbc:db2://localhost:25000/test:currentSchema=DB2ADMIN;";
//
// // Database credentials
// static final String USER = "db2admin";
// static final String PASS = "pass";
//
// // lists to store movie information
// private List<String> similarIDs = new ArrayList<>(); // movie IDs of recommended titles
// private List<String> similarTitle = new ArrayList<>(); // movie titles of recommended IDs
// private List<String> movieNames = new ArrayList<>(); // names of movies that are "like" the user's search string
// private List<String> movieIDs = new ArrayList<>();
// private List<String> movieID = new ArrayList<>(); // singular movie ID
//
// // returns the list of similar movie titles based on the movie name
// public List<String> getSimilarMovieTitles(String movieName) {
// // gets the movie names that match the user's search string
// movieNames = runQuery("select title from filtered_movies_tmbd where lower(title) like '%"+movieName.toLowerCase() +"%'", "title");
//
// // if the movie names list has only one movie, find similar movies
// if (movieNames.size() == 1) {
// // get the movie id of the movie name
// movieID = runQuery("select movie_id from filtered_movies_tmbd where lower(title) like '" + movieName.toLowerCase() + "'", "movie_id");
//
// // get the movie ids of the similar movies
// similarIDs = runQuery("Select movie_id from pearsons_correlations where ID_" + movieID.get(0) + " > 0.5", "movie_id");
//
// // get the movie titles of the similar movies
// for (int i = 0; i < similarIDs.size(); i++) {
// similarTitle = runQuery("select title from filtered_movies_tmbd where movie_id = " + similarIDs.get(i), "title");
// }
// }
//
// // return the list of similar movie titles
// return similarTitle;
// }
}