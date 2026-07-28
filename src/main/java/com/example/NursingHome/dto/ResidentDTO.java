package com.example.NursingHome.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Base64;

public record ResidentDTO(
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        int roomNumber,
        String base64Image,
        @Pattern(regexp = "^[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z]\\d{6}[A-D]$",
                message = "Érvénytelen brit NI szám formátum!")
        String nationalInsuranceNumber
) {
    // Egyedi konstruktor, ami pontosan leképezi a régi logikádat
    public ResidentDTO(String firstName, String middleName, String lastName,
                       LocalDate dateOfBirth, int roomNumber, byte[] imageBytes, String nationalInsuranceNumber) {
        this(
                firstName,
                (middleName == null || middleName.isEmpty()) ? "" : middleName,
                lastName,
                dateOfBirth,
                roomNumber,
                (imageBytes != null && imageBytes.length > 0) ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes) : null,
                nationalInsuranceNumber
        );
    }
}



