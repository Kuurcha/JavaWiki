package com.university.wiki.login.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AuthController {
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/auth")
    String login(){

        return "auth!";
    }
}
