package com.movieapp.frontend;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.*;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            restTemplate.exchange("http://localhost:8083/api/users/register", HttpMethod.POST, entity, User.class).getBody();

            model.addAttribute("message", "User registration successful!");
            return "loginForm";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute("message", "Error: " + e.getResponseBodyAsString());
            } else {
                model.addAttribute("message", "An error occurred while registering the user. Please try again later.");
            }
            return "registerForm";
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while registering the user. Please try again later.");
            return "registerForm";
        }
    }

    @GetMapping("/login")
    public String loginUser() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user,@ModelAttribute("Movie")Movie movie, Model model, HttpSession session) {
        // Find user by username
        String username = user.getUsername(); //get the username enter by user
        HttpHeaders headers = new HttpHeaders();// create object
        HttpEntity<User> entity = new HttpEntity<User>(headers); //set object to entity
        User find = restTemplate.exchange("http://localhost:8083/api/users/getUserByUsername/"+username, HttpMethod.GET, entity, User.class).getBody(); // find the username in database
        Optional<User> userdata = Optional.ofNullable(find); //optional object to hold result method find

        //encrypted password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
        var myPassword = user.getPassword();
        var encodedPassword = encoder.encode(myPassword);
        var validPassword = encoder.matches(myPassword, encodedPassword); //check if password enter match with database

        if(userdata.isPresent()) //if username exist
        {
            if (encoder.matches(user.getPassword(), userdata.get().getPassword())) //if password enter match with database
            {
                if (userdata.get().getRoleType().equals("admin")) //check the position type of use
                {
                    session.setAttribute("username", user.getUsername()); //session with name
                    return "adminHome"; //redirect to home for admin
                }
                else
                {
                    session.setAttribute("userid", userdata.get().getUserId()); //session for id
                    session.setAttribute("username", user.getUsername()); //session for username

                    HttpHeaders headers1 = new HttpHeaders();
                    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                    // Create an HttpEntity object with the headers
                    HttpEntity<Movie> entity1 = new HttpEntity<Movie>(headers1);
                    // The response body is expected to be a list of Movie objects
                    List<Movie> movieList = restTemplate.exchange("http://localhost:8083/api/movies/getMovies", HttpMethod.GET, entity1, List.class).getBody();
                    // Add the list of Movie objects to the model under the key "movieList"
                    model.addAttribute("movieList", movieList);
                    return "userHome";

                }
            }
            else
            {
                model.addAttribute("invalid", "LOGIN INVALID!");
                return "loginForm";
            }
        }
        else
        {
            model.addAttribute("invalid", "USER NOT EXIST!");
            return "loginForm";
        }
    }

    @GetMapping("/adminHome")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/userHome")
    public String userHome(Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Create an HttpEntity object with the headers
        HttpEntity<Movie> entity = new HttpEntity<Movie>(headers);
        // The response body is expected to be a list of Movie objects
        List<Movie> movieList = restTemplate.exchange("http://localhost:8083/api/movies/getMovies", HttpMethod.GET, entity, List.class).getBody();
        // Add the list of Movie objects to the model under the key "movieList"
        model.addAttribute("movieList", movieList);
        return "userHome";
    }

    @GetMapping("/manageMovies")
    public String manageAdmin () {
        return "manageMovies";
    }

    @GetMapping("/manageUsers")
    public String manageUSer () {
        return "manageUsers";
    }


    //----------------ADMIN DASHBOARD : MANAGE USERS-------------------------//


    // In this view, a list of users will be displayed
    // The list of users is obtained through a GET request to the "/api/users/getUser" endpoint
    // The request headers are set to accept JSON responses
    // The response is stored in a list of User objects, and this list is added to the model under the key "userList"
    @GetMapping("/viewAllUsers")
    public String viewAllUsers(Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Create an HttpEntity object with the headers
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        // The response body is expected to be a list of User objects
        List<User> userList = restTemplate.exchange("http://localhost:8083/api/users/getUsers", HttpMethod.GET, entity, List.class).getBody();
        // Add the list of User objects to the model under the key "userList"
        model.addAttribute("userList", userList);
        return "viewAllUsers";
    }

    @GetMapping("/addUsers")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUsers";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        try {
            restTemplate.exchange("http://localhost:8083/api/users/addUser", HttpMethod.POST, request, User.class);
            model.addAttribute("successMessage", "User " + user.getUsername() + " added successfully!");
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Failed to add user. Please try again.");
        }
        return "addUsers";
    }
    
    @GetMapping("/updateUser")
    public String updateUser() {
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        HttpEntity<User> entity = new HttpEntity<>(user);
        try {
            restTemplate.exchange("http://localhost:8083/api/users/updateUser", HttpMethod.PUT, entity, User.class).getBody();
            model.addAttribute("updateMessage", "User " + user.getUserId() + " updated successfully!");
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Error updating user " + user.getUserId() + ": " + e.getMessage());
        }
        return "updateUser";
    }

    @PostMapping("/getUser")
    public String getUser(@RequestParam int userId, Model model,@ModelAttribute("user") User user)
    {
        HttpHeaders headers2 = new HttpHeaders();
        HttpEntity<User> entity2 = new HttpEntity<>(headers2);
        User list = restTemplate.exchange("http://localhost:8083/api/users/getUserById/"+userId, HttpMethod.GET, entity2, User.class).getBody();
        Optional<User> userEntity = Optional.ofNullable(list);
        if (userEntity.isPresent())
        {
            model.addAttribute("user", list);
            return "updateUser";
        }
        else
        {
            return "updateUser";
        }
    }

    @GetMapping("/deleteUser")
    public String deleteUser() {
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User user, Model model) {
        int userId = user.getUserId();
        try {
            restTemplate.exchange("http://localhost:8083/api/users/deleteUser/" + userId, HttpMethod.DELETE, null, String.class).getBody();
            model.addAttribute("successMessage", "User " + userId + " deleted successfully!");
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Failed to delete user " + userId + ". Please try again.");
        }
        return "deleteUser";
    }


    //----------------ADMIN DASHBOARD : MANAGE MOVIES-------------------------//

    @GetMapping("/viewAllMovies")
    public String viewAllMovies(Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Create an HttpEntity object with the headers
        HttpEntity<MoviesOp> entity = new HttpEntity<MoviesOp>(headers);
        // The response body is expected to be a list of MoviesOp objects
        List<MoviesOp> movieList = restTemplate.exchange("http://localhost:8083/api/moviesOp/getMovies", HttpMethod.GET, entity, List.class).getBody();
        // Add the list of MoviesOp objects to the model under the key "moviesOpList"
        model.addAttribute("movieList", movieList);
        return "viewAllMovies";
    }

//    @GetMapping("/moviesNav")
//    public String moviesNav(Model model, @RequestParam(defaultValue = "0") int page,
//                            @RequestParam(defaultValue = "15") int size) {
//        String url = "http://localhost:8083/api/moviesOp/moviesNav?page=" + page + "&size=" + size;
//        ResponseEntity<Page<MoviesOp>> response = restTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<Page<MoviesOp>>() {});
//        Page<MoviesOp> moviesPage = response.getBody();
//        model.addAttribute("movieList", moviesPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", moviesPage.getTotalPages());
//        return "movie-list";
//    }

    @GetMapping("/addMovies")
    public String addMovies(Model model) {
        model.addAttribute("movie", new MoviesOp());
        return "addMovies";
    }

    @PostMapping("/addMovie")
    public String addMovie(@ModelAttribute MoviesOp moviesOp, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MoviesOp> request = new HttpEntity<>(moviesOp, headers);
        try {
            restTemplate.exchange("http://localhost:8083/api/moviesOp/addMovie", HttpMethod.POST, request, MoviesOp.class);
            model.addAttribute("successMessage", "Movie " + moviesOp.getTitle() + " added successfully!");
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Failed to add movie. Please try again.");
        }
        return "addMovies";
    }

    @GetMapping("/updateMovie")
    public String updateMovie() {
        return "updateMovie";
    }

    @PostMapping("/updateMovie")
    public String updateMovie(@ModelAttribute("movie") MoviesOp moviesOp, Model model) {
        HttpEntity<MoviesOp> entity = new HttpEntity<>(moviesOp);
        try {
            restTemplate.exchange("http://localhost:8083/api/moviesOp/updateMovie", HttpMethod.PUT, entity, MoviesOp.class).getBody();
            model.addAttribute("updateMessage", "Movie " + moviesOp.getMovieId() + " updated successfully!");
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Error updating movie " + moviesOp.getMovieId() + ": " + e.getMessage());
        }
        return "updateMovie";
    }

    @PostMapping("/getMovie")
    public String getMoviesOp(@RequestParam int movieId, Model model,@ModelAttribute("movie") MoviesOp moviesOp)
    {
        HttpHeaders headers2 = new HttpHeaders();
        HttpEntity<MoviesOp> entity2 = new HttpEntity<>(headers2);
        MoviesOp list = restTemplate.exchange("http://localhost:8083/api/moviesOp/getMovieById/"+movieId, HttpMethod.GET, entity2, MoviesOp.class).getBody();
        Optional<MoviesOp> moviesOpEntity = Optional.ofNullable(list);
        if (moviesOpEntity.isPresent())
        {
            model.addAttribute("movie", list);
            return "updateMovie";
        }
        else
        {
            return "updateMovie";
        }
    }

    @GetMapping("/deleteMovie")
    public String deleteMovies() {
        return "deleteMovie";
    }

    @PostMapping("/deleteMovie")
    public String deleteMovie(@ModelAttribute MoviesOp moviesOp, Model model) {
        int movieId = moviesOp.getMovieId();
        try {
            restTemplate.exchange("http://localhost:8083/api/moviesOp/deleteMovie/" + movieId, HttpMethod.DELETE, null, String.class).getBody();
            model.addAttribute("successMessage", "MoviesOp " + movieId + " deleted successfully!");
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Failed to delete movie " + movieId + ". Please try again.");
        }
        return "deleteMoviesOp";
    }

    @GetMapping("/movieDetails")
    public String getMovieDetails(@RequestParam("movieId") int movieId, Model model) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Movie> entity = new HttpEntity<Movie>(headers);
        // Call the API to get the details of a movie from database with the specified movieId
        System.out.println("Enter movieId you want to search:");
        Movie movieList = restTemplate.exchange("http://localhost:8083/api/movies/getMovieById/"+movieId, HttpMethod.GET, entity, Movie.class).getBody();
        model.addAttribute("movieList", movieList);

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<SimilarMovie> entity2 = new HttpEntity<>(headers2);
        // Call the API to get the list of similar movies to the movie with the specified movieId
        System.out.println("Similar movies to the one you entered are: ");
        List<SimilarMovie> movieid = restTemplate.exchange("http://localhost:8083/api/similarMovie/" + movieId, HttpMethod.GET, entity2, new ParameterizedTypeReference<List<SimilarMovie>>() {}).getBody();
        System.out.println("-----------------------------------");
        System.out.println(movieid);

        HttpHeaders headers3 = new HttpHeaders();
        headers3.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Movie> entity3 = new HttpEntity<Movie>(headers3);
        List<Movie> moviesList = new ArrayList<>();
        for (SimilarMovie similar : movieid) {
            int movieId2 = similar.getMovieId();
            // Call the API to get the details of the similar movies
            List<Movie> similarMovie = restTemplate.exchange("http://localhost:8083/api/movies/getMoviesById/" + movieId2, HttpMethod.GET, entity3, new ParameterizedTypeReference<List<Movie>>() {}).getBody();
            System.out.println();
//            List<Movie> similarMovie = restTemplate.exchange("https://api.themoviedb.org/3/movie/862?api_key=3fd2be6f0c70a2a598f084ddfb75487c&language=en-US", HttpMethod.GET, entity3, new ParameterizedTypeReference<List<Movie>>() {}).getBody();
            moviesList.addAll(similarMovie);
        }
        model.addAttribute("moviesList", moviesList);
        return "movieDetails";
    }

    @PostMapping("/addRating")
    public String addRating (@RequestParam("movieId") int movieId, @RequestParam("userId") int userId, @RequestParam("rating") double rating, @ModelAttribute("rating") Rating ratingObject, Model model) {
        // Set the movieId, userId and rating in the rating object
        ratingObject.setMovieId(movieId);
        ratingObject.setUserId(userId);
        ratingObject.setRating(rating);

        HttpEntity<Rating> entity = new HttpEntity<>(ratingObject);
        // Call the REST API to add the movie to rating
        restTemplate.exchange("http://localhost:8083/api/ratings/addRating", HttpMethod.POST, entity, Rating.class).getBody();
        return "viewAllFavorites";
    }

    // GET mapping to add a movie to favorites
    @GetMapping("/addFavorite")
    public String addFavorite(@RequestParam("favoriteId") int favoriteId, @RequestParam("userId") int userId, @ModelAttribute("favorite") Favorite favorite, Model model) {
        // Set the movieId and userId in the favorite object
        favorite.setMovieId(favoriteId);
        favorite.setUserId(userId);
        HttpEntity<Favorite> entity = new HttpEntity<Favorite>(favorite);
        // Call the REST API to add the movie to favorites
        restTemplate.exchange("http://localhost:8083/api/favorites/addFavorite", HttpMethod.POST, entity, Favorite.class).getBody();
        return "userHome";
    }

    @GetMapping("/viewAllFavorites")
    public String viewAllFavorites(Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Create an HttpEntity object with the headers
        HttpEntity<Favorite> entity = new HttpEntity<>(headers);
        // The response body is expected to be a list of MoviesOp objects
        List<Favorite> favoriteList = restTemplate.exchange("http://localhost:8083/api/favorites/getFavorites", HttpMethod.GET, entity, List.class).getBody();
        // Add the list of MoviesOp objects to the model under the key "favoriteList"
        model.addAttribute("favoriteList", favoriteList);
        return "viewAllFavorites";
    }
}