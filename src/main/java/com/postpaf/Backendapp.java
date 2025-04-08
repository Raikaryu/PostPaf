package com.postpaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import me.paulschwarz.springdotenv.DotenvPropertySource;

import java.io.ObjectInputFilter;

@SpringBootApplication
public class Backendapp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

		// Add DotenvPropertySource to environment before registering components
		DotenvPropertySource.addToEnvironment(applicationContext.getEnvironment());

		applicationContext.register(ObjectInputFilter.Config.class);
		applicationContext.refresh();

		SpringApplication.run(Backendapp.class, args);
	}

}
