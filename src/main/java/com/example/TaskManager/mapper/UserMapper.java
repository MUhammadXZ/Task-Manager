package com.example.TaskManager.mapper;


import com.example.TaskManager.dto.user.UserRequest;
import com.example.TaskManager.dto.user.UserResponse;
import com.example.TaskManager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaces", ignore = true)
    @Mapping(target = "oauth2Provider", ignore = true)
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "maskPhone")
    UserResponse toResponse(User user);

    @Named("maskPhone")
    default String maskPhone(String phoneNumber) {
        if (phoneNumber == null) return null;
        return phoneNumber.replaceAll("(\\+\\d{1,3})(\\d{2,4})(\\d{4})", "$1****$3");
    }
}