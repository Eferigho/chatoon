package com.chaton.service.exception;
/*
 *@author Ovuefe
 *
 */

public class UserNotFoundByEmail extends RuntimeException {

        public UserNotFoundByEmail(String entity, String message){
            super(String.format("%s with the email of: %s cannot be found", entity, message));
        }

}
