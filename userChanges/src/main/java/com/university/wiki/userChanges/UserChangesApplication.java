package com.university.wiki.userChanges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserChangesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserChangesApplication.class, args);
	}

}
