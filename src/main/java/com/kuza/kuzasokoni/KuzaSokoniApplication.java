package com.kuza.kuzasokoni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KuzaSokoniApplication {

	public static void main(String[] args) {

		SpringApplication.run(KuzaSokoniApplication.class, args);
	}

}
