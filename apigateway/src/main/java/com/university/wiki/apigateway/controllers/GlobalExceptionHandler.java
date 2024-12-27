package com.university.wiki.apigateway.controllers;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorResponse handleAllExceptions(Exception ex) {

        HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof ErrorResponse errorResponse) {
            statusCode = errorResponse.getStatusCode();
        }


        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, ex.getMessage());
        problemDetail.setTitle("Unexpected Error");

        return new CustomErrorResponse(statusCode, problemDetail);
    }
}