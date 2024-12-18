package com.university.wiki.apigateway.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/api/test")
    public ResponseEntity<String> getPublicKey()  {
        return ResponseEntity.ok("test");
    }
}
