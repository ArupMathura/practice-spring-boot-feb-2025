package com.example.revisionSpringBoot.mapper;

import com.example.revisionSpringBoot.dto.UserRequest;
import com.example.revisionSpringBoot.dto.UserResponse;
import com.example.revisionSpringBoot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring") // Enables Spring to inject this mapper
public interface AutoUserMapper {

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password")
    })
    User mapToUserEntity(UserRequest userRequest);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "email", target = "email")
    })
    UserResponse mapToUserResponse(User user);
}
