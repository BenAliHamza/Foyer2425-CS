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

public class EtudiantTest {

    private static final Logger logger = LoggerFactory.getLogger(EtudiantTest.class);

    private Etudiant etudiant;
    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    public void setUp() {
        // Setup the Reservation entities
        reservation1 = new Reservation("R1", LocalDate.of(2024, 9, 1), true, new ArrayList<>());
        reservation2 = new Reservation("R2", LocalDate.of(2023, 9, 1), false, new ArrayList<>());

        // Setup the Etudiant entity
        etudiant = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Doe")
                .prenomEt("John")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 1, 15))
                .reservations(Arrays.asList(reservation1, reservation2))
                .build();

        // Link the Etudiant with Reservations
        reservation1.getEtudiants().add(etudiant);
        reservation2.getEtudiants().add(etudiant);
    }

    @Test
    public void testEtudiantProperties() {
        logger.info("\u001B[34mTesting Etudiant properties\u001B[0m"); // Blue color

        assertNotNull(etudiant);
        assertEquals("Doe", etudiant.getNomEt());
        assertEquals("John", etudiant.getPrenomEt());
        assertEquals(12345678L, etudiant.getCin());
        assertEquals("ESPRIT", etudiant.getEcole());
        assertEquals(LocalDate.of(2000, 1, 15), etudiant.getDateNaissance());
    }

    @Test
    public void testReservationsAssociation() {
        logger.info("\u001B[32mTesting Etudiant-Reservation associations\u001B[0m"); // Green color

        assertNotNull(etudiant.getReservations());
        assertEquals(2, etudiant.getReservations().size());
        assertTrue(etudiant.getReservations().contains(reservation1));
        assertTrue(etudiant.getReservations().contains(reservation2));
    }

    @Test
    public void testEtudiantBuilderPattern() {
        logger.info("\u001B[35mTesting Etudiant Builder pattern\u001B[0m"); // Magenta color

        Etudiant newEtudiant = Etudiant.builder()
                .idEtudiant(2L)
                .nomEt("Smith")
                .prenomEt("Jane")
                .cin(87654321L)
                .ecole("IHEC")
                .dateNaissance(LocalDate.of(1999, 5, 20))
                .reservations(new ArrayList<>())
                .build();

        assertNotNull(newEtudiant);
        assertEquals("Smith", newEtudiant.getNomEt());
        assertEquals("Jane", newEtudiant.getPrenomEt());
        assertEquals(87654321L, newEtudiant.getCin());
        assertEquals("IHEC", newEtudiant.getEcole());
        assertEquals(LocalDate.of(1999, 5, 20), newEtudiant.getDateNaissance());
    }

    @Test
    public void testEtudiantSettersAndGetters() {
        logger.info("\u001B[33mTesting Etudiant Setters and Getters\u001B[0m"); // Yellow color

        etudiant.setNomEt("Updated Name");
        etudiant.setPrenomEt("Updated Surname");
        etudiant.setEcole("Updated School");

        assertEquals("Updated Name", etudiant.getNomEt());
        assertEquals("Updated Surname", etudiant.getPrenomEt());
        assertEquals("Updated School", etudiant.getEcole());
    }

    @Test
    public void testEtudiantToString() {
        logger.info("\u001B[31mTesting Etudiant toString method\u001B[0m"); // Red color

        String etudiantString = etudiant.toString();
        logger.debug("Etudiant toString: {}", etudiantString);

        assertTrue(etudiantString.contains("Doe"));
        assertTrue(etudiantString.contains("John"));
    }

    @Test
    public void testReservationAssociation() {
        logger.info("\u001B[34mTesting Reservation and Etudiant relationship\u001B[0m"); // Blue color

        assertNotNull(reservation1.getEtudiants());
        assertTrue(reservation1.getEtudiants().contains(etudiant));

        assertNotNull(reservation2.getEtudiants());
        assertTrue(reservation2.getEtudiants().contains(etudiant));
    }
}

