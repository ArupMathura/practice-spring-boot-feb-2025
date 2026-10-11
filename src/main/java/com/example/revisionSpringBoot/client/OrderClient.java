package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.OrderResponse;
import com.example.revisionSpringBoot.dto.ServiceCallResult;

import java.util.List;

//public interface OrderClient {
//    List<OrderResponse> getOrdersByUserId(Long userId);
//}

public interface OrderClient {
    ServiceCallResult<List<OrderResponse>> getOrdersByUserId(Long userId);
}
