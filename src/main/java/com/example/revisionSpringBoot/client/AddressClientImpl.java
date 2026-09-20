package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.AddressResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.UnknownContentTypeException;

import java.util.Collections;
import java.util.List;

@Component
@Profile("!test")
public class AddressClientImpl implements AddressClient {

    private final RestClient restClient;

    public AddressClientImpl(
            RestClient.Builder restClientBuilder,
            @Value("${address.service.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public List<AddressResponse> getAddressesByUserId(Long userId) {

        try {
            return restClient
                    .get()
                    .uri("/api/addresses/user/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<AddressResponse>>() {
                    });
        } catch (UnknownContentTypeException e) {
            return Collections.emptyList();
        }
    }
}
