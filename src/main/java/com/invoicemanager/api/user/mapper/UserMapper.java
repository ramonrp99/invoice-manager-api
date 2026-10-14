package com.invoicemanager.api.user.mapper;

import com.invoicemanager.api.user.dto.*;
import com.invoicemanager.api.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public AdminUserResponse toAdminUserResponse(User user) {
        return new AdminUserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreated_at(), user.getIs_active());
    }

    public User toEntity(AdminCreateUserRequest req) {
        User user = new User();

        user.setName(req.name());
        user.setEmail(req.email());

        return user;
    }

    public void updateEntity(User user, UpdateUserRequest req) {
        if(req.name() != null) user.setName(req.name());
        if(req.email() != null) user.setEmail(req.email());
    }

    public void updateEntity(User user, AdminUpdateUserRequest req) {
        if(req.name() != null) user.setName(req.name());
        if(req.email() != null) user.setEmail(req.email());
        if(req.role() != null) user.setRole(req.role());
        if(req.is_active() != null) user.setIs_active(req.is_active());
    }
}
