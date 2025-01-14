package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    @Test
    void testAddOrUpdate() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addOrUpdate(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("Doe", result.getNomEt());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testFindAll() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(Collections.singletonList(new Etudiant()));

        // Act
        List<Etudiant> result = etudiantService.findAll();

        // Assert
        assertFalse(result.isEmpty());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");

        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Doe", result.getNomEt());
        verify(etudiantRepository, times(1)).findById(1L);
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
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);

        doNothing().when(etudiantRepository).delete(etudiant);

        // Act
        etudiantService.delete(etudiant);

        // Assert
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testSelectJPQL() {
        // Arrange
        when(etudiantRepository.selectJPQL("Doe")).thenReturn(Collections.singletonList(new Etudiant()));

        // Act
        List<Etudiant> result = etudiantService.selectJPQL("Doe");

        // Assert
        assertFalse(result.isEmpty());
        verify(etudiantRepository, times(1)).selectJPQL("Doe");
    }
}