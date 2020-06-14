package main.mrs;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
  
@EnableScheduling
@SpringBootApplication
@EnableAsync //ukljucena podrska za izvrsavanje asinhronih zadataka 
public class Mrs2020T10Application {
	public static void main(String[] args) {
		SpringApplication.run(Mrs2020T10Application.class, args);
	}

}
