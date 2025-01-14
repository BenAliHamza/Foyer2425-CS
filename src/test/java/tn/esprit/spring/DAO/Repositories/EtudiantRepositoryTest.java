package tn.esprit.spring.DAO.Repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.DAO.Entities.Etudiant;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EtudiantRepositoryTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    void testFindByNomEt() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 1));
        etudiantRepository.save(etudiant);

        // Act
        List<Etudiant> result = etudiantRepository.findByNomEt("Doe");

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("Doe", result.get(0).getNomEt());
    }

    @Test
    void testSelectJPQL() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 1));
        etudiantRepository.save(etudiant);

        // Act
        List<Etudiant> result = etudiantRepository.selectJPQL("Doe");

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("Doe", result.get(0).getNomEt());
    }
}