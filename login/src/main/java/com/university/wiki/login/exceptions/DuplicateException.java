package com.university.wiki.login.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DuplicateException extends RuntimeException {
    private String message;
}
