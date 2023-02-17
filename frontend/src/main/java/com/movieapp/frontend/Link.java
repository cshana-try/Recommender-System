package com.movieapp.frontend;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Link {

    @Id
    @GeneratedValue
    private int id;

    private int movieId;

    private int imdbId;

    private int tmdbId;

}
