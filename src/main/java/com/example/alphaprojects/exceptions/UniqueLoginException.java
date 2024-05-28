package com.example.alphaprojects.exceptions;

public class UniqueLoginException extends RuntimeException{

    public UniqueLoginException(String message){
        super(message);
    }
}
