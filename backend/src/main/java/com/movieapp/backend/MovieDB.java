package com.movieapp.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieDB {
    //connection parameters
    static final String JDBC_DRIVER = "com.ibm.db2.jcc.DB2Driver";
    static final String DB_URL ="jdbc:db2://localhost:25000/test:currentSchema=DB2ADMIN;";
    static final String USER = "db2admin";
    static final String PASS = "pass";



    public static void main(String[] args) {

        List<String> similarIDs = new ArrayList<>(); // movie IDs of recommended titles
        List<String> similarTitle = new ArrayList<>(); // movie titles of recommended IDs
        List<String> movieNames = new ArrayList<>(); // MOvie names that are "like" thr user's search string
        List<String> movieIDs = new ArrayList<>();
        List<String> movieID = new ArrayList<>(); // singular movie ID
        String continueOption = "y";

        //Keep running until the user type "n"
        while (continueOption.charAt(0) == 'y' || continueOption.charAt(0) == 'Y'){
            String movieName = Inquiry("Name the movie that you's like to check out: ");

            //check what movie the user wants to check out
            movieNames = RunQuery("select title from filtered_movies_tmbd where lower(title) like '%"+movieName.toLowerCase() +"%'", "title");

            // in case there are more than one movie with similar titles
            if (movieNames.size() >1){
                System.out.println("Your search for " + movieName + " returned:" );

                // list down all filtered_movies_tmdb with similar titles
                for (int i = 0 ; i < movieNames.size(); i++){
                    System.out.println(ConsoleColors.BLUE + " - " + movieNames.get(i));
                    System.out.print(ConsoleColors.RESET);
                }
                System.out.println(ConsoleColors.YELLOW +"\n...so please be specific (copy paste if need be) and try again!");
                System.out.print(ConsoleColors.RESET);
                continueOption = Inquiry("\nWould you like to try again? [y/n]").toLowerCase();

            }
            // in case there are no titles even vaguely similar
            else if (movieNames.size()==0) {
                System.out.println(ConsoleColors.RED + "Your search for '" +
                        ConsoleColors.RED_BOLD_BRIGHT + movieName.toUpperCase() +
                        ConsoleColors.RED +"' returned no results!!!" );
                System.out.print(ConsoleColors.RESET);
                continueOption = Inquiry("\nWould you like to tray again? [y/n]").toLowerCase();
            }

            //perfect scenario: the user's selection returns a single title
            else{
                movieID = RunQuery("select movie_id from filtered_movies_tmbd where lower(title) like '"+movieName.toLowerCase() +"'", "movie_id");

                // do a search with a 50% similarity or more
                similarIDs = RunQuery("Select movie_id from pearsons_correlations where ID_"+ movieID.get(0) +" > 0.5","movie_id");

                // recommended titles
                System.out.println("Since you liked " +
                        ConsoleColors.GREEN_BOLD_BRIGHT + movieName +
                                ConsoleColors.RESET + ", you may also like: ");
                for (int i = 0 ; i < similarIDs.size(); i++){
                    similarTitle = RunQuery("select title from filtered_movies_tmbd where movie_id = "+similarIDs.get(i), "title");
                    for (int j = 0 ; j < similarTitle.size(); j++){
                        System.out.println(ConsoleColors.YELLOW + " - " +similarTitle.get(j));
                        System.out.print(ConsoleColors.RESET);
                    }
                }
                continueOption = Inquiry("\nWould you like to tray again? [y/n]").toLowerCase();
            }
        }
    }

    class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE

        // Bold
        public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
        public static final String RED_BOLD = "\033[1;31m";    // RED
        public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
        public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
        public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
        public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
        public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
        public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

        // Underline
        public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
        public static final String RED_UNDERLINED = "\033[4;31m";    // RED
        public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
        public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
        public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
        public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
        public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
        public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

        // Background
        public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
        public static final String RED_BACKGROUND = "\033[41m";    // RED
        public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
        public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
        public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
        public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
        public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
        public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

        // High Intensity
        public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
        public static final String RED_BRIGHT = "\033[0;91m";    // RED
        public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
        public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
        public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
        public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
        public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
        public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

        // Bold High Intensity
        public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
        public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
        public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
        public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
        public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
        public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
        public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

        // High Intensity backgrounds
        public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
        public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
        public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
        public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
        public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
        public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
        public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
        public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    }

    //Get input from user in the console
    static String Inquiry(String userQuery){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println(userQuery);
        String userInput = myObj.nextLine();  // Read user input
        System.out.println("Please wait....");
        return userInput;
    }

    // run query and return the result in a List of Strings
    static List<String> RunQuery(String myQuery, String myColumn){
        Connection conn = null;
        Statement stmt = null;
        List<String> result = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = myQuery;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                result.add(rs.getString(myColumn));
            }
            rs.close();

            // in case shit hits the fan!
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return result;
    }
}
