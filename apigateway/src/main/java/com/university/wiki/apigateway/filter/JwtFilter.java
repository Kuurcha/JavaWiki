package com.university.wiki.apigateway.filter;

import com.university.wiki.apigateway.service.PublicKeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Component
@EnableWebFluxSecurity
@AllArgsConstructor
public class JwtFilter implements WebFilter {

    private final PublicKeyService publicKeyService;

    private String getTokenFromRequest(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private Mono<Void> internalServerError(ServerWebExchange exchange, String errorMessage) {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);

        DataBuffer buffer = exchange.getResponse().bufferFactory()
                .wrap(errorMessage.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private Mono<Void> unauthorizedError(ServerWebExchange exchange, String errorMessage) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        DataBuffer buffer = exchange.getResponse().bufferFactory()
                .wrap(errorMessage.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = getTokenFromRequest(exchange);
        if (token != null && !token.isEmpty() && StringUtils.hasText(token)) {
            PublicKey publicKey = null;
            try {
                publicKey = publicKeyService.getPublicKey();
                if (publicKey == null) {
                    return unauthorizedError(exchange,"Public key is missing.");
                }
                try {
                    return validateToken(token, publicKey)
                            .flatMap(result -> {
                                if (!result.valid()) {
                                    return unauthorizedError(exchange, result.errorMessage());
                                }
                                return chain.filter(exchange);
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException | InterruptedException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<ValidationResult> validateToken(String token, PublicKey publicKey) {
        return Mono.fromCallable(() -> {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(publicKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                return validateClaims(claims);
            } catch (ExpiredJwtException e) {
                return ValidationResult.failure("JWT token is expired");
            } catch (MalformedJwtException e) {
                return ValidationResult.failure("Invalid JWT token signature");
            } catch (Exception e) {
                return ValidationResult.failure("Invalid JWT token");
            }
        });
    }


    private ValidationResult validateClaims(Claims claims) {
        try {
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                return ValidationResult.failure("JWT token is expired");
            }

/*
            String issuer = claims.getIssuer();
            if (issuer == null || !issuer.equals("expectedIssuer")) {
                return ValidationResult.failure("Invalid JWT token issuer");
            }

            String subject = claims.getSubject();
            if (subject == null || subject.isEmpty()) {
                return ValidationResult.failure("JWT token has no subject");
            }
*/

            return ValidationResult.success();
        } catch (Exception e) {
            return ValidationResult.failure("Unexpected error during claim validation: " + e.getMessage());
        }
    }


}