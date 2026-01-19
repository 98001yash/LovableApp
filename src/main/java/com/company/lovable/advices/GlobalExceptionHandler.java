package com.company.lovable.advices;


import com.company.lovable.exceptions.BusinessConflictException;
import com.company.lovable.exceptions.ai.AiOutputInvalidException;
import com.company.lovable.exceptions.ai.AiPolicyViolationException;
import com.company.lovable.exceptions.ai.AiProviderUnavailableException;
import com.company.lovable.exceptions.ai.AiRateLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AiOutputInvalidException.class)
    public ResponseEntity<ApiResponse<?>> handleAiOutputInvalid(AiOutputInvalidException ex) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(AiProviderUnavailableException.class)
    public ResponseEntity<ApiResponse<?>> handleAiProviderUnavailable(AiProviderUnavailableException ex) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message("AI service is temporarily unavailable. Please try again later.")
                .build();

        return buildErrorResponseEntity(apiError);
    }



    @ExceptionHandler(AiPolicyViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleAiPolicyViolation(AiPolicyViolationException ex) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(AiRateLimitException.class)
    public ResponseEntity<ApiResponse<?>> handleAiRateLimit(AiRateLimitException ex) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(BusinessConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessConflict(BusinessConflictException ex) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }


    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}