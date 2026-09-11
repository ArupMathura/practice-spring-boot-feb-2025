package com.example.revisionSpringBoot.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(
        scripts = "/test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)

class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateUser() throws Exception {

        // Arrange
        String requestJson = """
                {
                    "firstName": "John",
                    "lastName": "Smith",
                    "email": "john.test@gmail.com",
                    "password": "abcd1234"
                }
                """;

        String expectedJson = """
                {
                    "id": 32,
                    "firstName": "John",
                    "lastName": "Smith",
                    "email": "john.test@gmail.com"
                }
                """;

        // Act + Assert
        /*MvcResult result = mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("Status: " + result.getResponse().getStatus());
        System.out.println("Response: " + result.getResponse().getContentAsString());*/
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetUserById() throws Exception {

        // Arrange
        String expectedJson = """
            {
                "id": 2,
                "firstName": "Existing2",
                "lastName": "TestUser2",
                "email": "existing2@test.com"
            }
            """;

        // Act + Assert
        mockMvc.perform(
                        get("/api/users/{id}", 2)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        // Arrange
        String expectedJson = """
                [
                  {
                    "id": 1,
                    "firstName": "Existing",
                    "lastName": "TestUser",
                    "email": "existing@test.com"
                  },
                  {
                    "id": 2,
                    "firstName": "Existing2",
                    "lastName": "TestUser2",
                    "email": "existing2@test.com"
                  }
                ]
            """;

        // Act + Assert
        mockMvc.perform(
                        get("/api/users")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldUpdateUserById() throws Exception {

        // Arrange
        String requestJson = """
                {
                    "firstName": "John",
                    "lastName": "Smith",
                    "email": "john.test@gmail.com",
                    "password": "abcd1234"
                }
                """;

        String expectedJson = """
                {
                    "id": 2,
                    "firstName": "John",
                    "lastName": "Smith",
                    "email": "john.test@gmail.com"
                }
                """;

        // Act + Assert
        mockMvc.perform(
                        put("/api/users/{id}", 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shoulddeleteUserById() throws Exception {
        // Act + Assert
        mockMvc.perform(
                        delete("/api/users/{id}", 2)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }


    @Test
    void shouldUpdateUserByEmail() throws Exception {
        // Arrange
        String requestJson = """
                {
                    "firstName": "John",
                    "lastName": "Smith"
                }
                """;

        String expectedJson = """
                {
                  "msg": "firstname - \\"John\\" and lastname - \\"Smith\\" updated",
                  "id": 2,
                  "email": "existing2@test.com"
                }
                """;

        // Act + Assert
        mockMvc.perform(
                        put("/api/users/update")
                                .queryParam("email", "existing2@test.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}