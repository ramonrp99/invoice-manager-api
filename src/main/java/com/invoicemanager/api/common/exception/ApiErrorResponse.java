package com.invoicemanager.api.common.exception;

public record ApiErrorResponse(
        ApiError error,
        String message
) {
}
