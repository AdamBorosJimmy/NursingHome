package com.example.NursingHome.repository;

import com.example.NursingHome.entity.Residents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Residents, Long> {
    @Query(value = "SELECT * FROM public.residents WHERE room_number = :roomNumber", nativeQuery = true)
    Optional<Residents> findByRoomNumber(@Param("roomNumber") int roomNumber);
    Optional<Residents> findFirstByLastNameContainingIgnoreCase( String lastName);
    Optional<Residents> findFirstByMiddleNameContainingIgnoreCase( String middleName);
    Optional<Residents> findByNationalInsuranceNumber(String niNumber);


}
