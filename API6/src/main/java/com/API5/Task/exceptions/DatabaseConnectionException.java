package com.API5.Task.exceptions;

public class DatabaseConnectionException extends RuntimeException{
    DatabaseConnectionException(String message){
        super(message);
    }
}
