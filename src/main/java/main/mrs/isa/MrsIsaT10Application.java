package main.mrs.isa;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
  
@EnableScheduling
@SpringBootApplication
@EnableAsync //ukljucena podrska za izvrsavanje asinhronih zadataka
public class MrsIsaT10Application {

	public static void main(String[] args) {
		SpringApplication.run(MrsIsaT10Application.class, args);
	}

}

 

