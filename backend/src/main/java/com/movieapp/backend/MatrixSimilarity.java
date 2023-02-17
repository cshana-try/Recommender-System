//package com.movieapp.backend;
//
//import jakarta.persistence.Table;
//
//import java.util.HashMap;
//import java.util.Map;
//@Table(name = "matrixtable")
//public class MatrixSimilarity {
//    private Map<Integer, Map<Integer, Double>> ratingsMatrix;
//    private int movieLimit = 100;
//
//    public MatrixSimilarity(Map<Integer, Map<Integer, Double>> ratingsMatrix) {
//        this.ratingsMatrix = ratingsMatrix;
//    }
//
//
//    public Map<Integer, Map<Integer, Double>> getSimilarityMatrix() {
//        Map<Integer, Map<Integer, Double>> similarityMatrix = new HashMap<>();
//        for (Map.Entry<Integer, Map<Integer, Double>> movie1 : ratingsMatrix.entrySet()) {
//            if (similarityMatrix.size() >= movieLimit) {
//                break;
//            }
//            int movieId1 = movie1.getKey();
//            Map<Integer, Double> movie1Ratings = movie1.getValue();
//            for (Map.Entry<Integer, Map<Integer, Double>> movie2 : ratingsMatrix.entrySet()) {
//                if (similarityMatrix.size() >= movieLimit) {
//                    break;
//                }
//                int movieId2 = movie2.getKey();
//                Map<Integer, Double> movie2Ratings = movie2.getValue();
//                if (movieId1 != movieId2) {
//                    similarityMatrix.putIfAbsent(movieId1, new HashMap<>());
//                    double similarity = calculateCosineSimilarity(movie1Ratings, movie2Ratings);
//                    similarityMatrix.get(movieId1).put(movieId2, similarity);
//                } else {
//                    similarityMatrix.putIfAbsent(movieId1, new HashMap<>());
//                    similarityMatrix.get(movieId1).put(movieId2, 1.0);
//                }
//            }
//        }
//        return similarityMatrix;
//    }
//
//    private double calculateCosineSimilarity(Map<Integer, Double> movie1Ratings, Map<Integer, Double> movie2Ratings) {
//        double dotProduct = 0;
//        double magnitude1 = 0;
//        double magnitude2 = 0;
//        for (Map.Entry<Integer, Double> movie1Rating : movie1Ratings.entrySet()) {
//            int userId = movie1Rating.getKey();
//            if (movie2Ratings.containsKey(userId)) {
//                dotProduct += movie1Rating.getValue() * movie2Ratings.get(userId);
//            }
//            magnitude1 += Math.pow(movie1Rating.getValue(), 2);
//        }
//        for (double movie2Rating : movie2Ratings.values()) {
//            magnitude2 += Math.pow(movie2Rating, 2);
//        }
//        magnitude1 = Math.sqrt(magnitude1);
//        magnitude2 = Math.sqrt(magnitude2);
//        double similarity = dotProduct / (magnitude1 * magnitude2);
//        return Math.round(similarity * 10.0) / 10.0;
//    }
//
//    private double calculatePearsonCorrelation(Map<Integer, Double> movie1Ratings, Map<Integer, Double> movie2Ratings) {
//        double movie1Sum = 0;
//        double movie2Sum = 0;
//        double movie1SquaredSum = 0;
//        double movie2SquaredSum = 0;
//        double movie1Movie2ProductSum = 0;
//        int n = 0;
//        for (Map.Entry<Integer, Double> movie1Rating : movie1Ratings.entrySet()) {
//            int userId = movie1Rating.getKey();
//            if (movie2Ratings.containsKey(userId)) {
//                double movie1RatingValue = movie1Rating.getValue();
//                double movie2RatingValue = movie2Ratings.get(userId);
//                movie1Sum += movie1RatingValue;
//                movie2Sum += movie2RatingValue;
//                movie1SquaredSum += Math.pow(movie1RatingValue, 2);
//                movie2SquaredSum += Math.pow(movie2RatingValue, 2);
//                movie1Movie2ProductSum += movie1RatingValue * movie2RatingValue;
//                n++;
//            }
//        }
//        double numerator = movie1Movie2ProductSum - (movie1Sum * movie2Sum / n);
//        double denominator = Math.sqrt((movie1SquaredSum - Math.pow(movie1Sum, 2) / n) * (movie2SquaredSum - Math.pow(movie2Sum, 2) / n));
//        if (denominator == 0) {
//            return 0;
//        }
//        double pearsonCorrelation = numerator / denominator;
//        return Math.round(pearsonCorrelation * 10.0) / 10.0;
//    }
//
//    public double calculateJaccardSimilarity(Map<Integer, Double> movie1Ratings, Map<Integer, Double> movie2Ratings) {
//        int union = 0;
//        int intersection = 0;
//        for (Map.Entry<Integer, Double> movie1Rating : movie1Ratings.entrySet()) {
//            int userId = movie1Rating.getKey();
//            if (movie2Ratings.containsKey(userId)) {
//                intersection++;
//            }
//            union++;
//        }
//        union += movie2Ratings.size();
//        return (double) intersection / union;
//    }
//
//}
