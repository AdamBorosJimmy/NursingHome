package com.example.NursingHome.exception;

public class NotFoundByNationalInsuranceNumberException extends RuntimeException{
    public NotFoundByNationalInsuranceNumberException(String message) {
        super(message);
    }
}
