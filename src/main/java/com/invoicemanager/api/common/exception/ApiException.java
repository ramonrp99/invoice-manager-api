package com.invoicemanager.api.common.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ApiError error;

    public ApiException(ApiError error) {
        this.error = error;
    }
}
