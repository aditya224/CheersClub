package com.project.CheersClub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.project.CheersClub.pojo","com.project.CheersClub.util","com.project.CheersClub.dao","com.project.CheersClub.controller","com.project.CheersClub.validator"})
public class CheersClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheersClubApplication.class, args);
	}

}
