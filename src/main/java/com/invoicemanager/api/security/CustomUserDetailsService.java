package com.invoicemanager.api.security;

import com.invoicemanager.api.user.entity.User;
import com.invoicemanager.api.user.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.is_active(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
