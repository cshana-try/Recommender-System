package com.movieapp.backend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filtered_movies")
public class MoviesOp {
    @Id
    @GeneratedValue
    private Integer movieId;
    private String title;
    private String genres;
    private String description;
    private String poster;

}