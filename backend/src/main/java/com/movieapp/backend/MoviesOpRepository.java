package com.movieapp.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesOpRepository extends JpaRepository <MoviesOp, Integer> {

}
