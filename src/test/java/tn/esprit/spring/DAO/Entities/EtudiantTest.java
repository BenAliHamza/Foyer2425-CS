package tn.esprit.spring.DAO.Entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    @Test
    void testEtudiantConstructor() {
        Etudiant etudiant = new Etudiant(1L, "Doe", "John");
        assertEquals(1L, etudiant.getIdEtudiant());
        assertEquals("Doe", etudiant.getNomEt());
        assertEquals("John", etudiant.getPrenomEt());
    }

    @Test
    void testEtudiantSetters() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(2L);
        etudiant.setNomEt("Smith");
        etudiant.setPrenomEt("Jane");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 1));

        assertEquals(2L, etudiant.getIdEtudiant());
        assertEquals("Smith", etudiant.getNomEt());
        assertEquals("Jane", etudiant.getPrenomEt());
        assertEquals(12345678L, etudiant.getCin());
        assertEquals("ESPRIT", etudiant.getEcole());
        assertEquals(LocalDate.of(2000, 1, 1), etudiant.getDateNaissance());
    }
}