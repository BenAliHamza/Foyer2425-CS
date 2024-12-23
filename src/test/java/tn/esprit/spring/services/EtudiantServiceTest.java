
package tn.esprit.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.*;
import tn.esprit.spring.DAO.Repositories.*;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class EtudiantServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(EtudiantServiceTest.class);

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    private EtudiantService etudiantService;

    private Etudiant etudiant;
    private Universite universite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiantService = new EtudiantService(etudiantRepository, universiteRepository);

        universite = new Universite(1L, "University 1", "Location 1", null);
        etudiant = new Etudiant(1L, "John Doe", "CS", universite);
    }

    @Test
    void testAddOrUpdateEtudiant() {
        logger.info("\u001B[34mStarting testAddOrUpdateEtudiant...\u001B[0m");

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant savedEtudiant = etudiantService.addOrUpdate(etudiant);

        assertThat(savedEtudiant).isNotNull();
        assertThat(savedEtudiant.getNom()).isEqualTo("John Doe");

        logger.info("\u001B[32mEtudiant saved successfully with name: " + savedEtudiant.getNom() + "\u001B[0m");

        verify(etudiantRepository, times(1)).save(etudiant);
        logger.info("\u001B[34mEnding testAddOrUpdateEtudiant...\u001B[0m");
    }

    @Test
    void testFindAllEtudiants() {
        logger.info("\u001B[34mStarting testFindAllEtudiants...\u001B[0m");

        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant));

        List<Etudiant> etudiants = etudiantService.findAll();

        assertThat(etudiants).hasSize(1);
        assertThat(etudiants.get(0).getNom()).isEqualTo("John Doe");

        logger.info("\u001B[32mFound etudiants: " + etudiants.size() + "\u001B[0m");

        verify(etudiantRepository, times(1)).findAll();
        logger.info("\u001B[34mEnding testFindAllEtudiants...\u001B[0m");
    }

    @Test
    void testFindByIdEtudiant() {
        logger.info("\u001B[34mStarting testFindByIdEtudiant...\u001B[0m");

        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant foundEtudiant = etudiantService.findById(1L);

        assertThat(foundEtudiant).isNotNull();
        assertThat(foundEtudiant.getNom()).isEqualTo("John Doe");

        logger.info("\u001B[32mFound etudiant: " + foundEtudiant.getNom() + "\u001B[0m");

        verify(etudiantRepository, times(1)).findById(1L);
        logger.info("\u001B[34mEnding testFindByIdEtudiant...\u001B[0m");
    }

    @Test
    void testDeleteByIdEtudiant() {
        logger.info("\u001B[34mStarting testDeleteByIdEtudiant...\u001B[0m");

        doNothing().when(etudiantRepository).deleteById(1L);

        etudiantService.deleteById(1L);

        verify(etudiantRepository, times(1)).deleteById(1L);
        logger.info("\u001B[32mEtudiant with ID 1 deleted successfully.\u001B[0m");

        logger.info("\u001B[34mEnding testDeleteByIdEtudiant...\u001B[0m");
    }

    @Test
    void testDeleteEtudiant() {
        logger.info("\u001B[34mStarting testDeleteEtudiant...\u001B[0m");

        doNothing().when(etudiantRepository).delete(etudiant);

        etudiantService.delete(etudiant);

        verify(etudiantRepository, times(1)).delete(etudiant);
        logger.info("\u001B[32mEtudiant deleted successfully.\u001B[0m");

        logger.info("\u001B[34mEnding testDeleteEtudiant...\u001B[0m");
    }

    @Test
    void testAffecterEtudiantAUniversite() {
        logger.info("\u001B[34mStarting testAffecterEtudiantAUniversite...\u001B[0m");

        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(universiteRepository.findByNomUniversite("University 1")).thenReturn(universite);
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite updatedUniversite = etudiantService.affecterEtudiantAUniversite(1L, "University 1");

        assertThat(updatedUniversite.getEtudiants()).contains(etudiant);

        logger.info("\u001B[32mEtudiant successfully assigned to university: " + updatedUniversite.getNomUniversite() + "\u001B[0m");

        verify(universiteRepository, times(1)).save(universite);
        logger.info("\u001B[34mEnding testAffecterEtudiantAUniversite...\u001B[0m");
    }

    @Test
    void testDesaffecterEtudiantDeUniversite() {
        logger.info("\u001B[34mStarting testDesaffecterEtudiantDeUniversite...\u001B[0m");

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite updatedUniversite = etudiantService.desaffecterEtudiantDeUniversite(1L);

        assertThat(updatedUniversite.getEtudiants()).doesNotContain(etudiant);
        logger.info("\u001B[32mEtudiant successfully disassociated from university.\u001B[0m");

        verify(universiteRepository, times(1)).save(universite);
        logger.info("\u001B[34mEnding testDesaffecterEtudiantDeUniversite...\u001B[0m");
    }
}
