package com.example.NursingHome.controller;

import com.example.NursingHome.dto.ResidentDTO;
import com.example.NursingHome.entity.MedicalRecord;
import com.example.NursingHome.entity.Residents;
import com.example.NursingHome.repository.ResidentRepository; // 👈 1. Beimportáljuk a rendes Interfészt!
import com.example.NursingHome.service.ResidentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/residents") // 👈 Figyelem: Az URL-ek /api/residents-szel fognak kezdődni!
@CrossOrigin(origins = "*")
public class ResidentController {

    private final ResidentService service;
    private final ResidentRepository residentRepository; // 👈 2. Átírtuk az interfész típusára

    // 3. A konstruktorban is a rendes interfészt kérjük a Springtől
    public ResidentController(ResidentService service,
                              ResidentRepository residentRepository) {
        this.service = service;
        this.residentRepository = residentRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Residents>> getAllResidents() {
        // 4. Az interfészt hívjuk meg
        List<Residents> residents = residentRepository.findAll();
        return ResponseEntity.ok(residents);
    }

    @PostMapping("/{residentId}/medical-record")
    public ResponseEntity<?> saveMedicalRecord(@PathVariable Long residentId,
                                               @RequestBody MedicalRecord medicalRecord) {
        // 5. Itt is az interfészt használjuk a kereséshez
        return residentRepository.findById(residentId)
                .map(resident -> {
                    Residents updatedResident =
                            service.saveResidentWithMedicalRecord(resident, medicalRecord);
                    return ResponseEntity.ok(updatedResident);
                })
                .orElse(ResponseEntity.notFound().build()); // 6. Itt érdemes .notFound()-ot küldeni ok() helyett, ha nincs lakó
    }

    @GetMapping("/room/{roomNumber}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResidentDTO> getPersonByRoom(@PathVariable int roomNumber) {
        Residents resident = service.getResidentByRoomNumber(roomNumber);

        ResidentDTO residentDTO = convertToDTO(resident);
        return ResponseEntity.ok(residentDTO);

    }

    @GetMapping("/lastName/{lastName}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResidentDTO> getPersonByLastName(@PathVariable String lastName) {
        Residents resident = service.getResidentByLastName(lastName);

        ResidentDTO residentDTO = convertToDTO(resident);
        return ResponseEntity.ok(residentDTO);

    }

    @GetMapping("/middleName/{middleName}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResidentDTO> getPersonByMiddleName(@PathVariable String middleName) {
        Residents resident = service.getResidentByMiddleName(middleName);

        ResidentDTO residentDTO = convertToDTO(resident);
        return ResponseEntity.ok(residentDTO);
    }


    @PostMapping(value = "/with-photo", consumes = "multipart/form-data")
    public ResponseEntity<?> createResidentWithProfilePicture(
            @RequestParam("firstName") String firstName,
            @RequestParam(value = "middleName", required = false) String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam("roomNumber") int roomNumber,
            @RequestParam(value = "profilePicture", required = false) MultipartFile file,
            @RequestParam("nationalInsuranceNumber") String nationalInsuranceNumber) {


        try {
            Residents savedProfile = service.createResidentWithPicture(
                    firstName, middleName, lastName, dateOfBirth, roomNumber, file, nationalInsuranceNumber);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "{roomNumber}/photo", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProfilePicture(
            @PathVariable int roomNumber,
            @RequestParam("profilePicture") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Picture has not been uploaded.");
            }
            Residents updatedResident = service.updateResidentPhoto(roomNumber, file);
            return ResponseEntity.ok(updatedResident);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PutMapping(value = "/room/{roomNumber}/photo", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProfilePictureByRoom(
            @PathVariable("roomNumber") int roomNumber,
            @RequestParam("profilePicture") MultipartFile file) {
        try {
            // Validation check to make sure a file was actually sent
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected.");
            }
            Residents updatedResident = service.updateResidentPhotoByRoom(roomNumber, file);
            return ResponseEntity.ok(updatedResident);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private ResidentDTO convertToDTO(Residents resident) {
        return new ResidentDTO(
                resident.getFirstName(),
                resident.getMiddleName(),
                resident.getLastName(),
                resident.getDateOfBirth(),
                resident.getRoomNumber(),
                resident.getProfilePicture(),
                resident.getNationalInsuranceNumber());
    }


}