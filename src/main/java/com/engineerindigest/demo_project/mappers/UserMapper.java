package com.engineerindigest.demo_project.mappers;

import com.engineerindigest.demo_project.dto.UserPatchRequest;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.dto.UserRequest;
import com.engineerindigest.demo_project.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserRequest userRequest);

    User toUser(UserPatchRequest userRequest);

    UserResponse toUserResponse(User user);
}