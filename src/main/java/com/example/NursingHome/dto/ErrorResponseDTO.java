package com.example.NursingHome.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(LocalDateTime timestamp, int status, String error, String message, String path) {
}
