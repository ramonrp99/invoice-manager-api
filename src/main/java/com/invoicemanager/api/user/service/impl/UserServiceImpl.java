package com.invoicemanager.api.user.service.impl;

import com.invoicemanager.api.user.dto.CreateUserRequest;
import com.invoicemanager.api.user.dto.UpdateUserRequest;
import com.invoicemanager.api.user.dto.UserResponse;
import com.invoicemanager.api.user.entity.User;
import com.invoicemanager.api.user.mapper.UserMapper;
import com.invoicemanager.api.user.repository.UserRepository;
import com.invoicemanager.api.user.service.UserService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
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

    // GET
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(@NonNull Long id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")));
    }

    // POST
    @Override
    public UserResponse create(@NonNull CreateUserRequest req) {
        if(userRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email.");
        }

        User user = userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.password()));

        return userMapper.toResponse(userRepository.save(user));
    }

    // PUT
    @Override
    public UserResponse update(@NonNull Long id, @NonNull UpdateUserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        if(req.email() != null && !req.email().equals(user.getEmail()) && userRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email.");
        }

        userMapper.updateEntity(user, req);

        if(req.password() != null) {
            user.setPassword(passwordEncoder.encode(req.password()));
        }

        User updated = userRepository.save(user);

        return userMapper.toResponse(updated);
    }

    // DELETE
    @Override
    public void delete(@NonNull Long id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }

        userRepository.deleteById(id);
    }
}
