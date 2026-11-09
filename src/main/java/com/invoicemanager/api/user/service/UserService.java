package com.invoicemanager.api.user.service;

import com.invoicemanager.api.user.dto.*;
import org.springframework.data.domain.Page;

public interface UserService {

    // --- ADMIN ---
    Page<AdminUserResponse> findAll(AdminSearchUserRequest req);
    AdminUserResponse findById(Long id);
    AdminUserResponse createByAdmin(AdminCreateUserRequest req);
    AdminUserResponse updateByAdmin(Long id, AdminUpdateUserRequest req);
    void deleteByAdmin(Long id);

    // --- USER ---
    UserResponse findMe(Long id);
    UserResponse updateMe(Long id, UpdateUserRequest req);
    void changePassword(Long id, ChangePasswordRequest req);
}
