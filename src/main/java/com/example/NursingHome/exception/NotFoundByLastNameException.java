package com.example.NursingHome.exception;

public class NotFoundByLastNameException extends RuntimeException{
    public NotFoundByLastNameException(String message) {
        super(message);
    }
}
