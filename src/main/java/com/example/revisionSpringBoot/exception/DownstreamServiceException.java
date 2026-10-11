package com.example.revisionSpringBoot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DownstreamServiceException extends RuntimeException {

    private final String serviceName;
    private final HttpStatus status;

    public DownstreamServiceException(
            String serviceName,
            HttpStatus status,
            String message,
            Throwable cause) {

        super(message, cause);
        this.serviceName = serviceName;
        this.status = status;
    }
}
