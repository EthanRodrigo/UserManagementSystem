package com.ethan.system.users.utils;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest request){
        return createExceptionMessage(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED, request);
    }
    @ExceptionHandler(CredentialsExpiredException.class)
    public Map<String, Object> handleCredentialsExpired(CredentialsExpiredException exception, WebRequest request){
        return createExceptionMessage(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN, request);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, Object> handleUserNameNotFound(UsernameNotFoundException exception, WebRequest request){
        return createExceptionMessage(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED, request);
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    public Map<String, Object> handleAuthenticationServiceException(AuthenticationException exception, WebRequest request){
        return createExceptionMessage(exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public Map<String, Object> handleExpiredJwtException(ExpiredJwtException exception, WebRequest request){
        return createExceptionMessage(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN, request);
    }


    /* Creating a customized message for an error
    * */
    private Map<String, Object> createExceptionMessage(String exception, HttpStatus status, WebRequest request){
        Map<String, Object> error = new HashMap<>();
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);

        if(request instanceof ServletWebRequest){
            error.put("uri", ((ServletWebRequest)request).getRequest().getRequestURI());
        }
        error.put("message", exception);
        error.put("status_code", status.value());
        error.put("timestamp", timestamp);
        error.put("reason", status.getReasonPhrase());
        return  error;
    }
}
