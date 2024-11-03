package com.university.wiki.login.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

/**
 * DTO for {@link com.university.wiki.login.table.User}
 */
@Value
@Schema(description = "Запрос на авторизацию")
public class SignInRequestDTO {
    @Schema(description = "Имя пользователя", example = "John")
    @Size(message = "Имя пользователя должно содержать от 5 до 50 символов", min = 5, max = 50)
    @NotBlank(message = "Имя пользователя не может быть пустым")
    String username;

    @Schema(description = "Пароль", example = "y0ur_password")
    @Size(message = "Длина пароля должна быть от 8 до 255 символов", min = 8, max = 255)
    @NotBlank(message = "Пароль не может быть пустым")
    String password;
}