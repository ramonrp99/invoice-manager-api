package com.invoicemanager.api.security;

import lombok.Getter;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long userId;
    private final String username;
    private final String password;
    private final boolean is_active;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String username, String password, boolean is_active, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.is_active = is_active;
        this.authorities = authorities;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public @NonNull String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return is_active;
    }
}
