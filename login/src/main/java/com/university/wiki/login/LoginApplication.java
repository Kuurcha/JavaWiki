package com.university.wiki.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoginApplication {
	//Сделано с чопором на: https://habr.com/ru/articles/784508/
	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

}
