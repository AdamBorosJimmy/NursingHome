package com.example.NursingHome;

import com.example.NursingHome.entity.Residents;
import com.example.NursingHome.repository.ResidentRepository;
import com.example.NursingHome.service.ResidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResidenceServiceTest {
    // https://github.com/AdamBorosJimmy/NursingHome.git
    @Mock
    private ResidentRepository residentRepository;
    @InjectMocks
    private ResidentService residentService;

    @Test
    public void testGetResidentByRoomNumber_Success() {
        int roomNumber = 101;
        Residents fakeResident = new Residents();
        fakeResident.setFirstName("János");
        fakeResident.setRoomNumber(roomNumber);
        // A "nem valós ResidentServicenek" megmondjuk, ha a 101-es szobát keresik, akkor adja a vissza fenti lakót.
        when(residentRepository.findByRoomNumber(roomNumber))
                .thenReturn(Optional.of(fakeResident));
        // WHEN - Meghívjuk az IGAZI Service metódust
        Residents result = residentService.getResidentByRoomNumber(roomNumber);
        // THEN - Ellenorizzük, hogy jól muködött-e.
        assertNotNull(result); // Azt várjuk, hogy ne null-t kapjunk
        assertEquals("János",result.getFirstName()); // Azt várjuk, hogy a név János legyen
        assertEquals(101, result.getRoomNumber());

        // Ellenúrizzük, hogy a háttérben a service tényleg pontosan egyszer hívta-e meg a repository-t
        verify(residentRepository,times(1)).findByRoomNumber(roomNumber);
    }


}
