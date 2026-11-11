package com.invoicemanager.api.user.mapper;

import com.invoicemanager.api.user.dto.AdminSearchUserRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class UserSearchMapper {

    public Pageable toAdminUserPageable(AdminSearchUserRequest req) {
        return PageRequest.of(
                req.page(),
                req.size(),
                toAdminUserSort(req)
        );
    }

    private Sort toAdminUserSort(AdminSearchUserRequest req) {
        Sort.Direction direction = switch(req.sortDirection()) {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
        };

        return switch(req.sortBy()) {
            case ID -> Sort.by(
                    new Sort.Order(direction, "id")
            );
            case NAME -> Sort.by(
                    new Sort.Order(direction, "firstName"),
                    new Sort.Order(direction, "lastName")
            );
            case EMAIL -> Sort.by(
                    new Sort.Order(direction, "email")
            );
            case IS_ACTIVE -> Sort.by(
                    new Sort.Order(direction, "isActive")
            );
            case CREATED_AT -> Sort.by(
                    new Sort.Order(direction, "createdAt")
            );
        };
    }
}
