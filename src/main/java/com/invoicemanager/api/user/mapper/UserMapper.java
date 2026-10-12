package com.invoicemanager.api.user.mapper;

import com.invoicemanager.api.user.dto.CreateUserRequest;
import com.invoicemanager.api.user.dto.UpdateUserRequest;
import com.invoicemanager.api.user.dto.UserResponse;
import com.invoicemanager.api.user.entity.Role;
import com.invoicemanager.api.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public User toEntity(CreateUserRequest req) {
        User user = new User();

        user.setName(req.name());
        user.setEmail(req.email());
        user.setRole(Role.USER);

        return user;
    }

    public void updateEntity(User user, UpdateUserRequest req) {
        if(req.name() != null) user.setName(req.name());
        if(req.email() != null) user.setEmail(req.email());
    }
}
