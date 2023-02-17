package com.eurekaserver.movieappeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MovieappeurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieappeurekaApplication.class, args);
	}

}
