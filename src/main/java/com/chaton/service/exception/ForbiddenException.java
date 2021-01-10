package com.chaton.service.exception;

/*
 *@author Ovuefe
 *
 */

public class ForbiddenException extends RuntimeException {

    protected final Long serialVersionUUID = 1L;

    public ForbiddenException(){
        super();
    }

    public ForbiddenException(String forbiddenExceptionMessage){
        super(forbiddenExceptionMessage);
    }
}
