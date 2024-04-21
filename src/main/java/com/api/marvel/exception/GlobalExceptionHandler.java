package com.api.marvel.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handlerGeneralException(
            Exception ex,
            HttpServletRequest request,
            WebRequest webRequest)
    {
        if (ex instanceof HttpClientErrorException) {
            return this.handleHttpClientErrorException(
                    (HttpClientErrorException) ex,
                    request,
                    webRequest);
        } else if (ex instanceof AccessDeniedException) {
            return this.handleAccessDeniedException(
                    (AccessDeniedException) ex,
                    request,
                    webRequest);
        } else if (ex instanceof AuthenticationCredentialsNotFoundException) {
            return this.handleAuthenticationCredentialsNotFoundException(
                    (AuthenticationCredentialsNotFoundException) ex,
                    request,
                    webRequest);
        }

        return this.handleGenericException(ex, request, webRequest);
    }

    private ResponseEntity<ApiErrorDto> handleHttpClientErrorException(
            HttpClientErrorException ex,
            HttpServletRequest request,
            WebRequest webRequest)
    {
        String message = null;

        if (ex instanceof HttpClientErrorException.Forbidden) {
            message = "You don´t have access to this resource";
        } else if (ex instanceof HttpClientErrorException.Unauthorized) {
            message = "You don't have sufficient permissions for this resource";
        } else if (ex instanceof HttpClientErrorException.NotFound) {
            message = "Resource don't exist";
        } else if (ex instanceof HttpClientErrorException.Conflict) {
            message = "Conflict in the request";
        } else {
            message = "An unexpected error occurred";
        }

        ApiErrorDto errorDto = new ApiErrorDto(
                message,
                ex.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(errorDto);
    }

    private ResponseEntity<ApiErrorDto> handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request,
            WebRequest webRequest)
    {
        ApiErrorDto errorDto = new ApiErrorDto(
                "You don´t have access to this resource",
                ex.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
    }

    private ResponseEntity<ApiErrorDto> handleAuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException ex,
            HttpServletRequest request,
            WebRequest webRequest)
    {
        ApiErrorDto errorDto = new ApiErrorDto(
                "You don´t have access to this resource",
                ex.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    private ResponseEntity<ApiErrorDto> handleGenericException(
            Exception ex,
            HttpServletRequest request,
            WebRequest webRequest)
    {
        ApiErrorDto errorDto = new ApiErrorDto(
                "An unexpected error occurred",
                ex.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

}
