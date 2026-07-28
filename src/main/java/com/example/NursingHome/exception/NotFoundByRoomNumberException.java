package com.example.NursingHome.exception;

public class NotFoundByRoomNumberException extends RuntimeException{
    public NotFoundByRoomNumberException(String message) {
        super(message);
    }
}
