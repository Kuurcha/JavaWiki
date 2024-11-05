package com.university.wiki.login.exceptions.customHandlers;

import com.university.wiki.login.exceptions.DuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleException(DuplicateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Duplicate User Error");
        return new CustomErrorResponse(HttpStatus.CONFLICT, problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorResponse handleAllExceptions(Exception ex) {
        // Determine the HTTP status; default to 500 INTERNAL_SERVER_ERROR
        HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof ErrorResponse errorResponse) {
            statusCode = errorResponse.getStatusCode();
        }


        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, ex.getMessage());
        problemDetail.setTitle("Unexpected Error");

        return new CustomErrorResponse(statusCode, problemDetail);
    }
}