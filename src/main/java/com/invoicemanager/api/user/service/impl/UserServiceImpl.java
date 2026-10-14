package com.invoicemanager.api.user.service.impl;

import com.invoicemanager.api.user.dto.*;
import com.invoicemanager.api.user.entity.User;
import com.invoicemanager.api.user.mapper.UserMapper;
import com.invoicemanager.api.user.repository.UserRepository;
import com.invoicemanager.api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // --- ADMIN ---
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminUserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::toAdminUserResponse).toList();
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }

        userRepository.deleteById(id);
    }

    // --- USER ---
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("#id == authentication.principal.id")
    public UserResponse findMe(Long id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")));
    }

    @Override
    @PreAuthorize("#id == authentication.principal.id")
    public UserResponse updateMe(Long id, UpdateUserRequest req) {
        User user = findUser(id);

        validateUniqueEmail(req.email(), id);
        userMapper.updateEntity(user, req);

        User updated = userRepository.save(user);

        return userMapper.toUserResponse(updated);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));
    }

    private void validateUniqueEmail(String email) {
        if(email != null && userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email.");
        }
    }

    private void validateUniqueEmail(String email, Long id) {
        if(email != null && userRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email.");
        }
    }
}
