package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Email.EmailService;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        // Initialisation d'un étudiant pour les tests
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
    }

    @Test
    void testAddOrUpdate() {
        // Arrange
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
        
        // Act
        Etudiant result = etudiantService.addOrUpdate(etudiant);

        // Assert
        assertNotNull(result, "L'étudiant sauvegardé ne devrait pas être null");
        assertEquals("Doe", result.getNomEt(), "Le nom de l'étudiant devrait être 'Doe'");
        verify(etudiantRepository, times(1)).save(etudiant);
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testFindAll() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(Collections.singletonList(etudiant));

        // Act
        List<Etudiant> result = etudiantService.findAll();

        // Assert
        assertFalse(result.isEmpty(), "La liste des étudiants ne devrait pas être vide");
        assertEquals(1, result.size(), "La liste devrait contenir un seul étudiant");
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.findById(1L);

        // Assert
        assertNotNull(result, "L'étudiant trouvé ne devrait pas être null");
        assertEquals("Doe", result.getNomEt(), "Le nom de l'étudiant devrait être 'Doe'");
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(etudiantRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        Etudiant result = etudiantService.findById(2L);
        assertNull(result, "L'étudiant ne devrait pas être trouvé");
        verify(etudiantRepository, times(1)).findById(2L);
    }

    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(etudiantRepository).deleteById(1L);

        // Act
        etudiantService.deleteById(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(etudiantRepository).delete(etudiant);

        // Act
        etudiantService.delete(etudiant);

        // Assert
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testSelectJPQL() {
        // Arrange
        when(etudiantRepository.selectJPQL("Doe")).thenReturn(Collections.singletonList(etudiant));

        // Act
        List<Etudiant> result = etudiantService.selectJPQL("Doe");

        // Assert
        assertFalse(result.isEmpty(), "La liste des étudiants ne devrait pas être vide");
        assertEquals(1, result.size(), "La liste devrait contenir un seul étudiant");
        verify(etudiantRepository, times(1)).selectJPQL("Doe");
    }
}