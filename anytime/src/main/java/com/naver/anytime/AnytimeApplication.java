package com.naver.anytime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AnytimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnytimeApplication.class, args);
	}

}
