package tn.esprit.spring.DAO.Repositories;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EtudiantRepositoryTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    public void setUp() {
        etudiant1 = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Smith")
                .prenomEt("John")
                .cin(12345678L)
                .ecole("Ecole 1")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .build();

        etudiant2 = Etudiant.builder()
                .idEtudiant(2L)
                .nomEt("Doe")
                .prenomEt("Jane")
                .cin(87654321L)
                .ecole("Ecole 2")
                .dateNaissance(LocalDate.of(1998, 5, 15))
                .build();

        reservation1 = Reservation.builder()
                .idReservation("RES1")
                .anneeUniversitaire(LocalDate.of(2023, 1, 1))
                .estValide(true)
                .etudiants(Arrays.asList(etudiant1))
                .build();

        reservation2 = Reservation.builder()
                .idReservation("RES2")
                .anneeUniversitaire(LocalDate.of(2024, 1, 1))
                .estValide(false)
                .etudiants(Arrays.asList(etudiant2))
                .build();

        etudiant1.setReservations(Arrays.asList(reservation1));
        etudiant2.setReservations(Arrays.asList(reservation2));

        lenient().when(etudiantRepository.findByNomEt("Smith")).thenReturn(etudiant1);
        lenient().when(etudiantRepository.findByEcole("Ecole 1")).thenReturn(Arrays.asList(etudiant1));
        lenient().when(etudiantRepository.findByCin(12345678L)).thenReturn(etudiant1);
        lenient().when(etudiantRepository.findByReservationsAnneeUniversitaire(LocalDate.of(2023, 1, 1)))
                .thenReturn(Arrays.asList(etudiant1));
    }

    @Test
    public void testFindByNomEt() {
        Etudiant foundEtudiant = etudiantRepository.findByNomEt("Smith");
        assertNotNull(foundEtudiant, "Etudiant should not be null");
        assertEquals("Smith", foundEtudiant.getNomEt(), "Etudiant name does not match");
    }

    // Ajoutez d'autres tests ici...
}
