package com.ethan.system.users.utils.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalAuthenticationErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception){
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<String> handleCredentialsExpiredException(CredentialsExpiredException exception){
        return new ResponseEntity<>("Credentials have expired", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNameNotFoundException(UsernameNotFoundException exception){
        return new ResponseEntity<>("Username not found", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<String> handleAuthenticationServiceException(AuthenticationServiceException exception){
        return new ResponseEntity<>("Username not found", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        return new ResponseEntity<>("Username already taken", HttpStatus.UNAUTHORIZED);
    }
}
