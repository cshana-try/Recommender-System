package com.movieapp.frontend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MoviesOp {
    @Id
    @GeneratedValue
    private Integer movieId;
    private Integer tmdbid;
    private String title;
    private String genres;
    private String description;
    private String poster;

}
