package com.example.revisionSpringBoot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DownstreamServiceTimeoutException extends RuntimeException {

    private final String serviceName;

    public DownstreamServiceTimeoutException(
            String serviceName,
            String message,
            Throwable cause) {

        super(message, cause);
        this.serviceName = serviceName;
    }
}