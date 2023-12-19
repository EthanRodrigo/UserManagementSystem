package com.ethan.system.users.utils.ErrorHandling;

public class ExpiredJwtException extends RuntimeException{
    public ExpiredJwtException(String message){
        super(message);
    }
}
