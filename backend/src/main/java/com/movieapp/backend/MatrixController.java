//package com.movieapp.backend;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/similarity")
//public class MatrixController {
//    private final MatrixSimilarity similarity;
//
//    public MatrixController(MatrixSimilarity similarity) {
//        this.similarity = similarity;
//    }
//
//    @GetMapping("/cosine/{id1}/{id2}")
//    public double getCosineSimilarity(@PathVariable int id1, @PathVariable int id2) {
//        return similarity.getCosineSimilarity(id1, id2);
//    }
//
//    @GetMapping("/pearson")
//    public double calculatePearsonCorrelation(@RequestParam Map<Integer, Double> movie1Ratings,
//                                              @RequestParam Map<Integer, Double> movie2Ratings) {
//        return similarity.calculatePearsonCorrelation(movie1Ratings, movie2Ratings);
//    }
//
//    @GetMapping("/{id1}/{id2}")
//    public double getJaccardSimilarity(@PathVariable int id1, @PathVariable int id2) {
//        Map<Integer, Map<Integer, Double>> jaccardSimilarityMatrix = JaccardSimilarity.getJaccardSimilarityMatrix();
//        return jaccardSimilarityMatrix.get(id1).get(id2);
//    }
//
//    @GetMapping("/calculate")
//    public double calculateJaccardSimilarity(@RequestParam Map<Integer, Double> movie1Ratings,
//                                             @RequestParam Map<Integer, Double> movie2Ratings) {
//        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
//        return jaccardSimilarity.calculateJaccardSimilarity(movie1Ratings, movie2Ratings);
//    }
//}
