package com.university.wiki.apigateway.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@AllArgsConstructor
public class CustomErrorResponse implements ErrorResponse {
    private final HttpStatusCode statusCode;
    private final ProblemDetail body;
    @Override
    public HttpStatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public ProblemDetail getBody() {
        return this.body;
    }
}