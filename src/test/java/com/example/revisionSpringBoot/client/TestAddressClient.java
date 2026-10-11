package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.AddressResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


//@Component
//@Profile("test")
//public class TestAddressClient implements AddressClient {
//
//    private final ObjectMapper objectMapper;
//
//    public TestAddressClient(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public List<AddressResponse> getAddressesByUserId(Long userId) {
//
//        try {
//            InputStream inputStream =
//                    getClass()
//                            .getClassLoader()
//                            .getResourceAsStream(
//                                    "address-data/user-12-addresses.json"
//                            );
//
//            if (inputStream == null) {
//                throw new IllegalStateException(
//                        "Address test data not found"
//                );
//            }
//
//            return objectMapper.readValue(
//                    inputStream,
//                    new TypeReference<List<AddressResponse>>() {}
//            );
//
//        } catch (IOException e) {
//            throw new RuntimeException(
//                    "Unable to load address test data",
//                    e
//            );
//        }
//    }
//}
