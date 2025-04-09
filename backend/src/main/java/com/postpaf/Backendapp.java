package com.postpaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import me.paulschwarz.springdotenv.DotenvPropertySource;

import java.io.ObjectInputFilter;



@SpringBootApplication
public class Backendapp {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Backendapp.class);
		ConfigurableApplicationContext context = builder.build().run(args);

		// Ajoute le .env au bon moment
		DotenvPropertySource.addToEnvironment(context.getEnvironment());
	}
}


