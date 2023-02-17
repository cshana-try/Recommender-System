package com.movieapp.frontend;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Movie {
    @Id
    @GeneratedValue
    private Integer movieId;
    private Integer tmdbid;
    private String title;
    private String genres;
    private String description;
    private String poster;
}