package com.example.NursingHome.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "medical_records")
@Table
@Getter
@Setter

public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bloodType;
    private String chronicIllnesses;
    @OneToOne
    @JoinColumn(name = "resident_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore // 👈 Pontosan ide, közvetlenül a változó fölé!
    private Residents residents;
}
