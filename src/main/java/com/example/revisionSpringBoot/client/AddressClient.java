package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.AddressResponse;
import com.example.revisionSpringBoot.dto.ServiceCallResult;

import java.util.List;

//public interface AddressClient {
//    List<AddressResponse> getAddressesByUserId(Long userId);
//}

public interface AddressClient {
    ServiceCallResult<List<AddressResponse>> getAddressesByUserId(Long userId);
}