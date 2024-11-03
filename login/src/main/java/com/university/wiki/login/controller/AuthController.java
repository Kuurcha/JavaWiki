package com.university.wiki.login.controller;

import com.university.wiki.login.DTO.AuthResponseDTO;
import com.university.wiki.login.DTO.SingupRequestDTO;
import com.university.wiki.login.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public AuthResponseDTO signUp(@RequestBody SingupRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public AuthResponseDTO signIn(@RequestBody @Valid SingupRequestDTO request) {
        return authenticationService.signIn(request);
    }
}
