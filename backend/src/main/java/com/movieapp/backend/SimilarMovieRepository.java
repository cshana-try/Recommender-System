package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class SimilarMovieRepository{
    //Autowiring the JdbcTemplate to be able to perform database operations.
    @Autowired
    JdbcTemplate jdbcTemplate;

    //Defining a row mapper to map the result set obtained from the database to a SimilarMovie object.
    private RowMapper<SimilarMovie> rowMapper = (ResultSet rs, int rowNum) -> {
        SimilarMovie pearsons_correlations = new SimilarMovie();
        //Getting the movie id from the result set and setting it in the SimilarMovie object.
        pearsons_correlations.setMovieId(rs.getInt(1));

        return pearsons_correlations;
    };

    //Method to find the similar movies with a similarity of 50% or more.
    public List<SimilarMovie> findAll(int id) {
        //Dynamic column name based on the movie id.
        String column = "ID_" + id;
        //Executing the SQL query to get the similar movies. The query fetches the first 5 rows only.
        return jdbcTemplate.query("SELECT MOVIE_ID FROM pearsons_correlations WHERE " + column
                + " > 0.5 AND MOVIE_ID <> " + id + " ORDER BY " + column
                + " DESC FETCH FIRST 5 ROWS ONLY", rowMapper);
    }
}
