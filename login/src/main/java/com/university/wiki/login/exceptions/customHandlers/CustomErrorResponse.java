package com.university.wiki.login.exceptions.customHandlers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
