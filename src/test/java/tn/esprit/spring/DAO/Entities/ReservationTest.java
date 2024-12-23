package tn.esprit.spring.DAO.Entities;

import static org.junit.jupiter.api.Assertions.*;

        import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservationTest {

    private static final Logger logger = LoggerFactory.getLogger(ReservationTest.class);

    private Reservation reservation;
    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    public void setUp() {
        // Setup the Etudiant entities
        etudiant1 = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Doe")
                .prenomEt("John")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 1, 15))
                .reservations(new ArrayList<>())
                .build();

        etudiant2 = Etudiant.builder()
                .idEtudiant(2L)
                .nomEt("Smith")
                .prenomEt("Jane")
                .cin(87654321L)
                .ecole("IHEC")
                .dateNaissance(LocalDate.of(1999, 5, 20))
                .reservations(new ArrayList<>())
                .build();

        // Setup the Reservation entity
        reservation = Reservation.builder()
                .idReservation("R1")
                .anneeUniversitaire(LocalDate.of(2024, 9, 1))
                .estValide(true)
                .etudiants(Arrays.asList(etudiant1, etudiant2))
                .build();

        // Link the Reservation with Etudiants
        etudiant1.getReservations().add(reservation);
        etudiant2.getReservations().add(reservation);
    }

    @Test
    public void testReservationProperties() {
        logger.info("\u001B[34mTesting Reservation properties\u001B[0m"); // Blue color

        assertNotNull(reservation);
        assertEquals("R1", reservation.getIdReservation());
        assertEquals(LocalDate.of(2024, 9, 1), reservation.getAnneeUniversitaire());
        assertTrue(reservation.isEstValide());
    }

    @Test
    public void testEtudiantsAssociation() {
        logger.info("\u001B[32mTesting Reservation-Etudiant associations\u001B[0m"); // Green color

        assertNotNull(reservation.getEtudiants());
        assertEquals(2, reservation.getEtudiants().size());
        assertTrue(reservation.getEtudiants().contains(etudiant1));
        assertTrue(reservation.getEtudiants().contains(etudiant2));
    }

    @Test
    public void testReservationBuilderPattern() {
        logger.info("\u001B[35mTesting Reservation Builder pattern\u001B[0m"); // Magenta color

        Reservation newReservation = Reservation.builder()
                .idReservation("R2")
                .anneeUniversitaire(LocalDate.of(2023, 9, 1))
                .estValide(false)
                .etudiants(new ArrayList<>())
                .build();

        assertNotNull(newReservation);
        assertEquals("R2", newReservation.getIdReservation());
        assertEquals(LocalDate.of(2023, 9, 1), newReservation.getAnneeUniversitaire());
        assertFalse(newReservation.isEstValide());
    }

    @Test
    public void testReservationSettersAndGetters() {
        logger.info("\u001B[33mTesting Reservation Setters and Getters\u001B[0m"); // Yellow color

        reservation.setIdReservation("Updated R1");
        reservation.setAnneeUniversitaire(LocalDate.of(2025, 9, 1));
        reservation.setEstValide(false);

        assertEquals("Updated R1", reservation.getIdReservation());
        assertEquals(LocalDate.of(2025, 9, 1), reservation.getAnneeUniversitaire());
        assertFalse(reservation.isEstValide());
    }

    @Test
    public void testReservationToString() {
        logger.info("\u001B[31mTesting Reservation toString method\u001B[0m"); // Red color

        String reservationString = reservation.toString();
        logger.debug("Reservation toString: {}", reservationString);

        assertTrue(reservationString.contains("R1"));
        assertTrue(reservationString.contains("2024"));
    }

    @Test
    public void testEtudiantAssociation() {
        logger.info("\u001B[34mTesting Etudiant and Reservation relationship\u001B[0m"); // Blue color

        assertNotNull(etudiant1.getReservations());
        assertTrue(etudiant1.getReservations().contains(reservation));

        assertNotNull(etudiant2.getReservations());
        assertTrue(etudiant2.getReservations().contains(reservation));
    }
}

