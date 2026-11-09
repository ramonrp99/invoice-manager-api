package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.enums.UserRole;
import com.invoicemanager.api.user.enums.UserSortBy;
import com.invoicemanager.api.user.enums.UserSortDirection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AdminSearchUserRequest(
        String name,
        String email,
        UserRole role,
        Boolean isActive,

        @Min(0)
        Integer page,

        @Min(1) @Max(100)
        Integer size,

        UserSortBy sortBy,
        UserSortDirection sortDirection
) {
    public AdminSearchUserRequest {
        if(page == null) page = 0;
        if(size == null) size = 20;
        if(sortBy == null) sortBy = UserSortBy.ID;
        if(sortDirection == null) sortDirection = UserSortDirection.ASC;
    }
}
