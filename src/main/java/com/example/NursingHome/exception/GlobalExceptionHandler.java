package com.example.NursingHome.exception;

import com.example.NursingHome.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NotFoundByRoomNumberException.class,
            NotFoundByMiddleNameException.class,
            NotFoundByLastNameException.class,
            NotFoundByNationalInsuranceNumberException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleResidentNotFoundExceptions(
            RuntimeException exception, HttpServletRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}