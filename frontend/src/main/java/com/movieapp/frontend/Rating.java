package com.movieapp.frontend;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Rating {
    @Id
    @GeneratedValue
    private int id;
    private int userId;
    private int movieId;
    private double rating;
}
