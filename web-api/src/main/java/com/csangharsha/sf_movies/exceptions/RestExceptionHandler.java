package com.csangharsha.sf_movies.exceptions;

import io.jsonwebtoken.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiException> handleException(Exception exception) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiException> handleAuthenticationException(AuthenticationException authenticationException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, authenticationException.getMessage(), authenticationException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<ApiException> handleJwtException(JwtException jwtException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, jwtException.getMessage(), jwtException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(MalformedJwtException
            .class)
    protected ResponseEntity<ApiException> handleMalformedJwtException(MalformedJwtException malformedJwtException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, malformedJwtException.getMessage(), malformedJwtException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ApiException> handleExpiredJwtException(ExpiredJwtException expiredJwtException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, expiredJwtException.getMessage(), expiredJwtException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(SignatureException.class)
    @Order(2)
    protected ResponseEntity<ApiException> handleSignatureException(SignatureException signatureException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, signatureException.getMessage(), signatureException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    protected ResponseEntity<ApiException> handleUnsupportedJwtException(UnsupportedJwtException unsupportedJwtException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, unsupportedJwtException.getMessage(), unsupportedJwtException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage(), illegalArgumentException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiException> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, accessDeniedException.getMessage(), accessDeniedException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, resourceNotFoundException.getMessage(), resourceNotFoundException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiException> handleBadRequestException(BadRequestException badRequestException) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, badRequestException.getMessage(), badRequestException);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

}
