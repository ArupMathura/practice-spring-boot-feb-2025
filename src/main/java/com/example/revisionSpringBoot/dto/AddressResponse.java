package com.example.revisionSpringBoot.dto;

public record AddressResponse(
        Long id,
        Long userId,
        String addressLine1,
        String city,
        String state,
        String zipCode
) {
}
