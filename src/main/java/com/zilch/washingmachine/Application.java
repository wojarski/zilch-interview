package com.zilch.washingmachine;

import com.zilch.washingmachine.config.MainSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MainSpringConfiguration.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
