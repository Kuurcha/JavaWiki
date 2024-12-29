package com.university.wiki.login.service;

import com.university.wiki.login.DTO.AuthResponseDTO;
import com.university.wiki.login.DTO.SingupRequestDTO;
import com.university.wiki.login.table.User;
import com.university.wiki.login.table.info.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Transactional
    public AuthResponseDTO signUp(SingupRequestDTO request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userService.create(user);

        var jwt = jwtService.generateToken(user);


        return new AuthResponseDTO(jwt, request.getUsername());
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public AuthResponseDTO signIn(SingupRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));

            var user = userService
                    .userDetailsService()
                    .loadUserByUsername(request.getUsername());

            var jwt = jwtService.generateToken(user);
            return new AuthResponseDTO(jwt, request.getUsername());

    }
        catch (UsernameNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
    }
        catch (BadCredentialsException e) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный логин или пароль");
    }
        catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Неизвестная ошибка во время аутентификации");
    }

    }
}
