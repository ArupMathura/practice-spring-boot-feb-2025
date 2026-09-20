package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.AddressResponse;

import java.util.List;

public interface AddressClient {
    List<AddressResponse> getAddressesByUserId(Long userId);
}
