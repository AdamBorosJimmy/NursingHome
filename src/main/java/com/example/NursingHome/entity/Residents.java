package com.example.NursingHome.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "residents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Residents {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "room_number", unique = true)
    private Integer roomNumber;
    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;
    @Column(name = "ni_number", unique = true, nullable = false,length = 9)
    private String nationalInsuranceNumber;
    @OneToOne(mappedBy = "residents",cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

}
