package com.invoicemanager.api.user.service.impl;

import com.invoicemanager.api.common.exception.ApiError;
import com.invoicemanager.api.common.exception.ApiException;
import com.invoicemanager.api.user.dto.*;
import com.invoicemanager.api.user.entity.User;
import com.invoicemanager.api.user.mapper.UserMapper;
import com.invoicemanager.api.user.mapper.UserSearchMapper;
import com.invoicemanager.api.user.repository.UserRepository;
import com.invoicemanager.api.user.service.UserService;
import com.invoicemanager.api.user.specification.AdminUserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserSearchMapper userSearchMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserSearchMapper userSearchMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userSearchMapper = userSearchMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // --- ADMIN ---
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AdminUserResponse> findAll(AdminSearchUserRequest req) {
        Pageable pageable = userSearchMapper.toAdminUserPageable(req);
        Specification<User> specification = Specification
                .where(AdminUserSpecification.containsName(req.name()))
                .and(AdminUserSpecification.containsEmail(req.email()))
                .and(AdminUserSpecification.hasRole(req.role()))
                .and(AdminUserSpecification.hasActive(req.isActive()));

        Page<User> users = userRepository.findAll(specification, pageable);

        return users.map(userMapper::toAdminUserResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse findById(Long id) {
        return userMapper.toAdminUserResponse(findUser(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse createByAdmin(AdminCreateUserRequest req) {
        validateUniqueEmail(req.email());

        User user = userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.password()));

        return userMapper.toAdminUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AdminUserResponse updateByAdmin(Long id, AdminUpdateUserRequest req) {
        User user = findUser(id);

        validateUniqueEmail(req.email(), id);
        userMapper.updateEntity(user, req);

        User updated = userRepository.save(user);

        return userMapper.toAdminUserResponse(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByAdmin(Long id) {
        if(!userRepository.existsById(id)) {
            throw new ApiException(ApiError.USER_NOT_FOUND);
        }

        userRepository.deleteById(id);
    }

    // --- USER ---
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("#id == authentication.principal.userId")
    public UserResponse findMe(Long id) {
        return userMapper.toUserResponse(findUser(id));
    }

    @Override
    @PreAuthorize("#id == authentication.principal.userId")
    public UserResponse updateMe(Long id, UpdateUserRequest req) {
        User user = findUser(id);

        validateUniqueEmail(req.email(), id);
        userMapper.updateEntity(user, req);

        User updated = userRepository.save(user);

        return userMapper.toUserResponse(updated);
    }

    @Override
    @PreAuthorize("#id == authentication.principal.userId")
    public void changePassword(Long id, ChangePasswordRequest req) {
        if(!req.newPassword().equals(req.confirmNewPassword())) {
            throw new ApiException(ApiError.USER_NEW_PASSWORD_CONFIRMATION_MISMATCH);
        }

        if(req.newPassword().equals(req.currentPassword())) {
            throw new ApiException(ApiError.USER_NEW_PASSWORD_SAME_AS_CURRENT);
        }

        User user = findUser(id);

        if(!passwordEncoder.matches(req.currentPassword(), user.getPassword())) {
            throw new ApiException(ApiError.USER_CURRENT_PASSWORD_INCORRECT);
        }

        user.setPassword(passwordEncoder.encode(req.newPassword()));

        userRepository.save(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiError.USER_NOT_FOUND));
    }

    private void validateUniqueEmail(String email) {
        if(email != null && userRepository.existsByEmailIgnoreCase(email)) {
            throw new ApiException(ApiError.USER_EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateUniqueEmail(String email, Long id) {
        if(email != null && userRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ApiException(ApiError.USER_EMAIL_ALREADY_EXISTS);
        }
    }
}
