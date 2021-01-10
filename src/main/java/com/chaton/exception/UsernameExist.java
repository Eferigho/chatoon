package com.chaton.exception;

public class UsernameExist  extends Throwable{
    public UsernameExist(String message){
        super(message);
    }
}
