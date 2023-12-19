package com.ethan.system.users.utils.ErrorHandling;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public static ResponseEntity<String> handleExpiredJWTException(ExpiredJwtException exception){
        return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
    }
}
