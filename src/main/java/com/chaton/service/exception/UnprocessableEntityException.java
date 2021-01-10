package com.chaton.service.exception;

/*
 *@author Ovuefe
 *
 */

public class UnprocessableEntityException extends RuntimeException {

    private static final long SerialVersionUUID=1L;

    public UnprocessableEntityException(){
        super();
    }

    public UnprocessableEntityException(String unprocessedEntityExceptionMessage){
        super(unprocessedEntityExceptionMessage);
    }
}
