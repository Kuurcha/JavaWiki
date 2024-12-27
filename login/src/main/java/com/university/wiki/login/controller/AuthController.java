package com.university.wiki.login.controller;

import com.university.wiki.login.DTO.AuthResponseDTO;
import com.university.wiki.login.DTO.SingupRequestDTO;
import com.university.wiki.login.exceptions.DuplicateException;
import com.university.wiki.login.service.AuthenticationService;
import com.university.wiki.login.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @GetMapping("/public-key")
    public ResponseEntity<String> getPublicKey()  {
        String publicKeyPem = jwtService.getPEMPublicKey();
        return ResponseEntity.ok(publicKeyPem);
    }


    @GetMapping("/hello")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World!");
        return response;
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public AuthResponseDTO signUp(@RequestBody SingupRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public AuthResponseDTO signIn(@RequestBody SingupRequestDTO request) {
        return authenticationService.signIn(request);
    }
}
