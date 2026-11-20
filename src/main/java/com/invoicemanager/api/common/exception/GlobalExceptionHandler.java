package com.invoicemanager.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception) {
        ApiError apiError = exception.getError();
        HttpStatus status = ApiError.getHttpStatus(apiError);
        ApiErrorResponse body = createApiErrorResponse(apiError);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiErrorResponse> handleExceptions(MethodArgumentTypeMismatchException exception) {
        ApiError apiError = ApiError.BAD_REQUEST;
        HttpStatus status = ApiError.getHttpStatus(apiError);

        String paramName = exception.getName();

        String requiredTypeName = "unknown";
        if(exception.getRequiredType() != null) {
            requiredTypeName = exception.getRequiredType().getSimpleName();
        }

        String providedTypeName = "unknown";
        if(exception.getValue() != null) {
            providedTypeName = exception.getValue().getClass().getSimpleName();
        }

        String message = String.format(
                "Invalid value for parameter '%s'. Expected type: '%s', but got: '%s'.",
                paramName, requiredTypeName, providedTypeName
        );

        ApiErrorResponse body = createApiErrorResponse(apiError, message);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

        ApiError apiError = ApiError.BAD_REQUEST;
        HttpStatus status = ApiError.getHttpStatus(apiError);
        ApiErrorResponse body = createApiErrorResponse(apiError, errors.toString());

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception exception) {
        ApiError apiError = ApiError.INTERNAL_SERVER_ERROR;
        HttpStatus status = ApiError.getHttpStatus(apiError);
        ApiErrorResponse body = createApiErrorResponse(apiError);

        return ResponseEntity.status(status).body(body);
    }

    private ApiErrorResponse createApiErrorResponse(ApiError error) {
        String message = ApiError.getMessage(error);

        return new ApiErrorResponse(error, message);
    }

    private ApiErrorResponse createApiErrorResponse(ApiError error, String message) {
        return new ApiErrorResponse(error, message);
    }
}
