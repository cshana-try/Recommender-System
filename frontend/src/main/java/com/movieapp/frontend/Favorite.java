package com.movieapp.frontend;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class Favorite {
    @Id
    @GeneratedValue
    private int favoriteId;
    private int movieId;
    private int userId;
}
