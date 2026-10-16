package com.invoicemanager.api.user.mapper;

import com.invoicemanager.api.user.dto.*;
import com.invoicemanager.api.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public AdminUserResponse toAdminUserResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.isActive()
        );
    }

    public User toEntity(AdminCreateUserRequest req) {
        User user = new User();

        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setEmail(req.email());

        return user;
    }

    public void updateEntity(User user, UpdateUserRequest req) {
        if(req.firstName() != null) user.setFirstName(req.firstName());
        if(req.lastName() != null) user.setLastName(req.lastName());
        if(req.email() != null) user.setEmail(req.email());
    }

    public void updateEntity(User user, AdminUpdateUserRequest req) {
        if(req.firstName() != null) user.setFirstName(req.firstName());
        if(req.lastName() != null) user.setLastName(req.lastName());
        if(req.email() != null) user.setEmail(req.email());
        if(req.role() != null) user.setRole(req.role());
        if(req.isActive() != null) user.setActive(req.isActive());
    }
}
