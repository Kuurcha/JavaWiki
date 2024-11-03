package com.university.wiki.login.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Value
@Builder
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class AuthResponseDTO   {
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzI1NiJ9.eyJSb2x...")
    String token;
}