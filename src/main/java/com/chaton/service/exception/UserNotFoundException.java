package com.chaton.service.exception;
/*
 *@author Ovuefe
 *
 */

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer Id){
        super("Could not find user with Id, " + Id);
    }
}
