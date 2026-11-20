package com.invoicemanager.api.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter(AccessLevel.PRIVATE)
public enum ApiError {
    // USER
    USER_NEW_PASSWORD_CONFIRMATION_MISMATCH(HttpStatus.BAD_REQUEST, "New password and confirmation do not match"),
    USER_NEW_PASSWORD_SAME_AS_CURRENT(HttpStatus.BAD_REQUEST, "New password must differ from the current password"),
    USER_CURRENT_PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "Current password is incorrect"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already in use"),

    // CUSTOMER
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not found"),
    CUSTOMER_NIF_ALREADY_EXISTS(HttpStatus.CONFLICT, "NIF already in use"),

    // PRODUCT
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    PRODUCT_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Name already in use"),

    // INVOICE
    INVOICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Invoice not found"),
    INVOICE_LINE_NOT_FOUND(HttpStatus.NOT_FOUND, "Invoice line not found"),
    INVOICE_NOT_DRAFT(HttpStatus.CONFLICT, "Invoice must be in draft status"),
    INVOICE_ALREADY_CANCELED(HttpStatus.CONFLICT, "Invoice is already canceled"),

    // GENERIC
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    ;

    private final HttpStatus status;
    private final String description;

    ApiError(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
    }

    public static HttpStatus getHttpStatus(ApiError error) {
        return Arrays.stream(ApiError.values())
                .filter(element -> element.equals(error))
                .findFirst()
                .map(ApiError::getStatus)
                .orElse(null);
    }

    public static String getMessage(ApiError error) {
        return Arrays.stream(ApiError.values())
                .filter(element -> element.equals(error))
                .findFirst()
                .map(ApiError::getDescription)
                .orElse(null);
    }

    public static ApiError getFromHttpStatus(HttpStatus code) {
        return Arrays.stream(ApiError.values())
                .filter(element -> element.getStatus().equals(code))
                .findFirst()
                .orElse(INTERNAL_SERVER_ERROR);
    }
}
