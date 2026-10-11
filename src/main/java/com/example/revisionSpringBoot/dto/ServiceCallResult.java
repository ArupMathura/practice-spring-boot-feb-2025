package com.example.revisionSpringBoot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCallResult<T> {

    private T data;
    private boolean hasData;
    private PartialResponseWarning warning;

    public static <T> ServiceCallResult<T> withData(T data) {
        boolean hasData = data != null;

        if (data instanceof java.util.Collection<?> collection) {
            hasData = !collection.isEmpty();
        }

        return new ServiceCallResult<>(
                data,
                hasData,
                null
        );
    }

    public static <T> ServiceCallResult<T> noData(
            String serviceName,
            String message) {

        return new ServiceCallResult<>(
                null,
                false,
                new PartialResponseWarning(
                        serviceName,
                        message
                )
        );
    }
}