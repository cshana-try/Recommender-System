package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/addFavorite")
    public Favorite addFavorite(@RequestBody Favorite favorite) {
        return favoriteService.save(favorite);
    }

    @GetMapping("/getFavorites")
    public List<Favorite> findAllMovies() {
        return favoriteService.getFavorites();
    }
    @GetMapping("/getFavoriteById/{id}")
    public List<Favorite> getFavoriteById(@PathVariable int id) {
        return favoriteService.getFavoritebyId(id);
    }
    @DeleteMapping("/deleteFavorite/{id}")
    public String deleteFavorite(@PathVariable int id) {
        return favoriteService.deleteFavorite(id);
    }
}
