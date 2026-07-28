package com.example.NursingHome.service;

import com.example.NursingHome.entity.MedicalRecord;
import com.example.NursingHome.entity.Residents;
import com.example.NursingHome.exception.NotFoundByLastNameException;
import com.example.NursingHome.exception.NotFoundByMiddleNameException;
import com.example.NursingHome.exception.NotFoundByNationalInsuranceNumberException;
import com.example.NursingHome.exception.NotFoundByRoomNumberException;
import com.example.NursingHome.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class ResidentService {
    @Autowired
    private final ResidentRepository residentRepository;


    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }
    @Transactional
    public Residents saveResidentWithMedicalRecord(Residents newResident, MedicalRecord newRecord) {
        // Összekörtjük a két objektumot (kétirányú kapcsolat)
        newResident.setMedicalRecord(newRecord);
        newRecord.setResidents(newResident);
        // Elmentjük a lakót. A CascadeType.ALL miatt a medical_records táblába is belekerül a karton!
        return residentRepository.save(newResident);
    }

    public Residents getResidentByRoomNumber(int roomNumber) {
        return residentRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new NotFoundByRoomNumberException("No resident found in room: " + roomNumber));
    }
    public Residents getResidentByRoomNumber(String niNumber) {
        return residentRepository.findByNationalInsuranceNumber(niNumber)
                .orElseThrow(() -> new NotFoundByNationalInsuranceNumberException("No resident found by NI number: "  +niNumber));
    }

    public Residents getResidentByLastName(String lastName) {
        return residentRepository.findFirstByLastNameContainingIgnoreCase(lastName)
                .orElseThrow(() -> new NotFoundByLastNameException("No resident found with last name: " + lastName));
    }
    public Residents getResidentByMiddleName(String middleName) {
        return residentRepository.findFirstByMiddleNameContainingIgnoreCase(middleName)
                .orElseThrow(() -> new NotFoundByMiddleNameException("No resident found with middle name: " + middleName));
    }

    public Residents createResident(Residents resident) {
        return residentRepository.save(resident);
    }


    public Residents createResidentWithPicture(String firstName, String middleName, String lastName,
                                               LocalDate dateOfBirth, int roomNumber, MultipartFile file,
                                               String nationalInsuranceNumber) {

        if(residentRepository.findByRoomNumber(roomNumber).isPresent()) {
            throw new IllegalArgumentException("Room " + roomNumber + " has already been occupied.");
        }
        String niRegex = "^[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z]\\d{6}[A-D]$";
        if(nationalInsuranceNumber == null || nationalInsuranceNumber.matches(niRegex)) {
            throw new IllegalArgumentException("Given NI number is possessed by another resident.");
        }


        Residents resident = new Residents();
        resident.setFirstName(firstName);
        resident.setMiddleName((middleName == null || middleName.isEmpty()) ? "" : middleName);
        resident.setLastName(lastName);
        resident.setDateOfBirth(dateOfBirth);
        resident.setRoomNumber(roomNumber);
        resident.setNationalInsuranceNumber(nationalInsuranceNumber);

        if (file != null && !file.isEmpty()) {
            try {
                resident.setProfilePicture(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save profile picture", e);
            }
        } else {
            resident.setProfilePicture(null);
        }

        return residentRepository.save(resident);
    }


    public Residents updateResidentPhoto(int roomNumber, MultipartFile file)  {
        Residents resident = residentRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() ->
                        new NotFoundByRoomNumberException("Resident not found with ID: " + roomNumber));
        try  {
            resident.setProfilePicture(file.getBytes());
            return residentRepository.save(resident);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile picture string data.");
        }
    }
    public Residents updateResidentPhotoByRoom(int roomNumber, MultipartFile file) {
        // 1. Look up the resident using your existing method signature
        Residents resident = getResidentByRoomNumber(roomNumber);

        // 2. Validate that the resident exists before modifying properties
        if (resident == null) {
            throw new IllegalArgumentException("No resident found currently occupying room: " + roomNumber);
        }

        try {
            // 3. Extract raw file bytes and update the entity model property
            resident.setProfilePicture(file.getBytes());

            // 4. Commit to the database using your JpaRepository
            return residentRepository.save(resident);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read and process incoming profile picture bytes", e);
        }
    }
}
