package com.chaton.service.exception;

/*
 *@author Ovuefe
 *
 */

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(String message){
        super(message);
    };
}
