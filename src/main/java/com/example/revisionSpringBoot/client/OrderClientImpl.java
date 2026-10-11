package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.OrderResponse;
import com.example.revisionSpringBoot.dto.ServiceCallResult;
import com.example.revisionSpringBoot.exception.DownstreamServiceException;
import com.example.revisionSpringBoot.exception.DownstreamServiceTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class OrderClientImpl implements OrderClient {

    private final WebClient webClient;

    public OrderClientImpl(
            @Qualifier("orderWebClient") WebClient webClient) {

        this.webClient = webClient;
    }

    @Override
    public ServiceCallResult<List<OrderResponse>> getOrdersByUserId(Long userId) {
        try {
//            List<OrderResponse> orders = webClient
//                    .get()
//                    .uri("/api/orders/user/{userId}", userId)
//                    .retrieve()
//                    .bodyToFlux(OrderResponse.class)
//                    .collectList()
//                    .block();

            List<OrderResponse> orders = webClient
                    .get()
                    .uri(uriBuilder -> {
                        URI uri = uriBuilder
                                .path("/api/orders/user/{userId}")
                                .build(userId);

                        log.info("Calling order service URL: {}", uri);
                        return uri;
                    })
                    .retrieve()
                    .bodyToFlux(OrderResponse.class)
                    .collectList()
                    .block();

            if (orders == null || orders.isEmpty()) {
                return ServiceCallResult.noData(
                        "ORDER_SERVICE",
                        "No orders found for this user"
                );
            }

            return ServiceCallResult.withData(orders);

        } catch (WebClientResponseException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.debug("No orders found for userId={}", userId);

                return ServiceCallResult.noData(
                        "ORDER_SERVICE",
                        "No orders found for this user"
                );
            }

            if (e.getStatusCode().is5xxServerError()) {
                log.error(
                        "Order service returned HTTP {}",
                        e.getStatusCode().value(),
                        e
                );

                throw new DownstreamServiceException(
                        "ORDER_SERVICE",
                        HttpStatus.BAD_GATEWAY,
                        "Order service returned HTTP "
                                + e.getStatusCode().value(),
                        e
                );
            }

            throw new DownstreamServiceException(
                    "ORDER_SERVICE",
                    HttpStatus.BAD_GATEWAY,
                    "Order service returned HTTP "
                            + e.getStatusCode().value(),
                    e
            );

        } catch (WebClientRequestException e) {
            log.error(
                    "Order service is unavailable or timed out for userId={}",
                    userId,
                    e
            );

            throw new DownstreamServiceTimeoutException(
                    "ORDER_SERVICE",
                    "Order service is unavailable or timed out",
                    e
            );
        }
    }

    /*@Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        try {
            return webClient.get()
                    .uri("/api/orders/user/{userId}", userId)
                    .retrieve()
                    .bodyToFlux(OrderResponse.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            // Handle 5xx errors - service is actually down
            if (e.getStatusCode().is5xxServerError()) {
                log.error("Order service returned server error: HTTP {}", e.getStatusCode().value());
                throw new DownstreamServiceException(
                        "ORDER_SERVICE",
                        HttpStatus.BAD_GATEWAY,
                        "Order service returned HTTP " + e.getStatusCode().value(),
                        e
                );
            }

            // Handle 4xx errors - 404 means no data, other 4xx are client errors
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.debug("No orders found for userId={}", userId);
                return Collections.emptyList();
            }

            // Other 4xx errors - log and return empty (could also throw if needed)
            log.warn("Order service returned client error: HTTP {} for userId={}", e.getStatusCode().value(), userId);
            return Collections.emptyList();
        } catch (ResourceAccessException e) {
            // Network timeout, connection refused - service is DOWN
            log.error("Order service is unavailable or timed out");
            throw new DownstreamServiceTimeoutException(
                    "ORDER_SERVICE",
                    "Order service is unavailable or timed out",
                    e
            );
        }
    }*/
}