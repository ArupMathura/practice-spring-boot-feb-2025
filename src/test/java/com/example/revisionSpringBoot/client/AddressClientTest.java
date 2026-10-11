package com.example.revisionSpringBoot.client;

import com.example.revisionSpringBoot.dto.AddressResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

//@SpringBootTest
//class AddressClientTest {
//
//    @Autowired
//    private AddressClient addressClient;
//
//    @Test
//    void shouldGetAddressesForUser() {
//
//        List<AddressResponse> addresses =
//                addressClient.getAddressesByUserId(12L);
//
//        // Verify number of addresses
//        assertThat(addresses).hasSize(2);
//
//        // Verify first address
//        AddressResponse firstAddress = addresses.get(0);
//
//        assertThat(firstAddress.id()).isEqualTo(101L);
//        assertThat(firstAddress.userId()).isEqualTo(12L);
//        assertThat(firstAddress.addressLine1())
//                .isEqualTo("12 Park Street");
//        assertThat(firstAddress.city())
//                .isEqualTo("Kolkata");
//        assertThat(firstAddress.state())
//                .isEqualTo("West Bengal");
//        assertThat(firstAddress.zipCode())
//                .isEqualTo("700016");
//
//
//        // Verify second address
//        AddressResponse secondAddress = addresses.get(1);
//
//        assertThat(secondAddress.id()).isEqualTo(102L);
//        assertThat(secondAddress.userId()).isEqualTo(12L);
//        assertThat(secondAddress.addressLine1())
//                .isEqualTo("45 Salt Lake");
//        assertThat(secondAddress.city())
//                .isEqualTo("Kolkata");
//        assertThat(secondAddress.state())
//                .isEqualTo("West Bengal");
//        assertThat(secondAddress.zipCode())
//                .isEqualTo("700091");
//    }
//}
