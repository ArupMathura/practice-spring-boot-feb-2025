package com.example.revisionSpringBoot.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {

        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setPath(request.getRequestURI());
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetails.setErrorcode("USER_NOT_FOUND");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDetails);
    }

    // We use this @ExceptionHandler annotation to handle the specific exception and return the custom error response back to the client.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
//                exception.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}

