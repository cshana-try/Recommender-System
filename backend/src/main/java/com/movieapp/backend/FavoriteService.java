package com.movieapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public List<Favorite> getFavoritebyId(int id) {
        return favoriteRepository.findByUserId(id);
    }

    public String deleteFavorite(int id) {
        favoriteRepository.deleteById(id);
        return "Favorites no " + id + " has been removed";
    }

    public List<Favorite> getFavorites() {
        return favoriteRepository.findAll();
    }
}
