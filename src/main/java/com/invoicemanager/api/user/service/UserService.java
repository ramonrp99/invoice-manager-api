package com.invoicemanager.api.user.service;

import com.invoicemanager.api.user.dto.*;

import java.util.List;

public interface UserService {

    // --- ADMIN ---
    List<AdminUserResponse> findAll();
    AdminUserResponse findById(Long id);
    AdminUserResponse createByAdmin(AdminCreateUserRequest req);
    AdminUserResponse updateByAdmin(Long id, AdminUpdateUserRequest req);
    void deleteByAdmin(Long id);

    // --- USER ---
    UserResponse findMe(Long id);
    UserResponse updateMe(Long id, UpdateUserRequest req);
}
