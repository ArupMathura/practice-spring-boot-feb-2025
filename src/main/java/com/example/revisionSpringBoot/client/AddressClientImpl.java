package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.AddressResponse;
import com.example.revisionSpringBoot.dto.ServiceCallResult;
import com.example.revisionSpringBoot.exception.DownstreamServiceException;
import com.example.revisionSpringBoot.exception.DownstreamServiceTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
@Profile("!test")
@Slf4j
public class AddressClientImpl implements AddressClient {

    private final RestClient restClient;

    public AddressClientImpl(
            RestClient.Builder restClientBuilder,
            @Value("${address.service.base-url}") String baseUrl) {

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public ServiceCallResult<List<AddressResponse>> getAddressesByUserId(Long userId) {
        try {
//            List<AddressResponse> addresses = restClient
//                    .get()
//                    .uri("/api/addresses/user/{userId}", userId)
//                    .retrieve()
//                    .body(new ParameterizedTypeReference<List<AddressResponse>>() {
//                    });
            List<AddressResponse> addresses = restClient
                    .get()
                    .uri(uriBuilder -> {
                        URI uri = uriBuilder
                                .path("/api/addresses/user/{userId}")
                                .build(userId);

                        log.info("Calling address URL: {}", uri);
                        return uri;
                    })
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<AddressResponse>>() {});

            if (addresses == null || addresses.isEmpty()) {
                return ServiceCallResult.noData(
                        "ADDRESS_SERVICE",
                        "No addresses found for this user"
                );
            }

            return ServiceCallResult.withData(addresses);

        } catch (RestClientResponseException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.debug("No addresses found for userId={}", userId);

                return ServiceCallResult.noData(
                        "ADDRESS_SERVICE",
                        "No addresses found for this user"
                );
            }

            if (e.getStatusCode().is5xxServerError()) {
                throw new DownstreamServiceException(
                        "ADDRESS_SERVICE",
                        HttpStatus.BAD_GATEWAY,
                        "Address service returned HTTP "
                                + e.getStatusCode().value(),
                        e
                );
            }

            throw new DownstreamServiceException(
                    "ADDRESS_SERVICE",
                    HttpStatus.BAD_GATEWAY,
                    "Address service returned HTTP "
                            + e.getStatusCode().value(),
                    e
            );

        } catch (ResourceAccessException e) {
            throw new DownstreamServiceTimeoutException(
                    "ADDRESS_SERVICE",
                    "Address service is unavailable or timed out",
                    e
            );
        }
    }

    /*@Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        try {
            return restClient
                    .get()
                    .uri("/api/addresses/user/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<AddressResponse>>() {
                    });

        } catch (RestClientResponseException e) {
            // Handle 5xx errors - service is actually down
            if (e.getStatusCode().is5xxServerError()) {
                log.error("Address service returned server error: HTTP {}", e.getStatusCode().value());
                throw new DownstreamServiceException(
                        "ADDRESS_SERVICE",
                        HttpStatus.BAD_GATEWAY,
                        "Address service returned HTTP " + e.getStatusCode().value(),
                        e
                );
            }

            // Handle 4xx errors - check if it's 404 (no data) or other client error
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.debug("No addresses found for userId={}", userId);
                return Collections.emptyList();
            }

            // Other 4xx errors - treat as bad gateway
            log.error("Address service returned client error: HTTP {}", e.getStatusCode().value());
            throw new DownstreamServiceException(
                    "ADDRESS_SERVICE",
                    HttpStatus.BAD_GATEWAY,
                    "Address service returned HTTP " + e.getStatusCode().value(),
                    e
            );
        } catch (ResourceAccessException e) {
            // Network timeout, connection refused - service is DOWN
            log.error("Address service is unavailable or timed out");
            throw new DownstreamServiceTimeoutException(
                    "ADDRESS_SERVICE",
                    "Address service is unavailable or timed out",
                    e
            );
        }
    }*/
}