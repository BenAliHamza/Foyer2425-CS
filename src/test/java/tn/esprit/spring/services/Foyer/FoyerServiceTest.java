package tn.esprit.spring.services.Foyer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.*;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Foyer.FoyerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class FoyerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FoyerServiceTest.class);

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    private FoyerService foyerService;

    private Foyer foyer;
    private Universite universite;
    private Bloc bloc1;
    private Bloc bloc2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foyerService = new FoyerService(foyerRepository, foyerRepository, universiteRepository, blocRepository);

        universite = new Universite(1L, "University 1", "Location 1", null);
        foyer = new Foyer(1L, "Foyer 1", 200, universite, Arrays.asList());
        bloc1 = new Bloc(1L, "Bloc 1", 50, foyer, Arrays.asList());
        bloc2 = new Bloc(2L, "Bloc 2", 70, foyer, Arrays.asList());
    }

    @Test
    void testAddOrUpdateFoyer() {
        logger.info("\u001B[34mStarting testAddOrUpdateFoyer...\u001B[0m");

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer savedFoyer = foyerService.addOrUpdate(foyer);

        assertThat(savedFoyer).isNotNull();
        assertThat(savedFoyer.getNomFoyer()).isEqualTo("Foyer 1");

        logger.info("\u001B[32mFoyer saved successfully with name: " + savedFoyer.getNomFoyer() + "\u001B[0m");

        verify(foyerRepository, times(1)).save(foyer);
        logger.info("\u001B[34mEnding testAddOrUpdateFoyer...\u001B[0m");
    }

    @Test
    void testFindAllFoyers() {
        logger.info("\u001B[34mStarting testFindAllFoyers...\u001B[0m");

        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));

        List<Foyer> foyers = foyerService.findAll();

        assertThat(foyers).hasSize(1);
        assertThat(foyers.get(0).getNomFoyer()).isEqualTo("Foyer 1");

        logger.info("\u001B[32mFound foyers: " + foyers.size() + "\u001B[0m");

        verify(foyerRepository, times(1)).findAll();
        logger.info("\u001B[34mEnding testFindAllFoyers...\u001B[0m");
    }

    @Test
    void testFindByIdFoyer() {
        logger.info("\u001B[34mStarting testFindByIdFoyer...\u001B[0m");

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer foundFoyer = foyerService.findById(1L);

        assertThat(foundFoyer).isNotNull();
        assertThat(foundFoyer.getNomFoyer()).isEqualTo("Foyer 1");

        logger.info("\u001B[32mFound foyer: " + foundFoyer.getNomFoyer() + "\u001B[0m");

        verify(foyerRepository, times(1)).findById(1L);
        logger.info("\u001B[34mEnding testFindByIdFoyer...\u001B[0m");
    }

    @Test
    void testDeleteByIdFoyer() {
        logger.info("\u001B[34mStarting testDeleteByIdFoyer...\u001B[0m");

        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.deleteById(1L);

        verify(foyerRepository, times(1)).deleteById(1L);
        logger.info("\u001B[32mFoyer with ID 1 deleted successfully.\u001B[0m");

        logger.info("\u001B[34mEnding testDeleteByIdFoyer...\u001B[0m");
    }

    @Test
    void testDeleteFoyer() {
        logger.info("\u001B[34mStarting testDeleteFoyer...\u001B[0m");

        doNothing().when(foyerRepository).delete(foyer);

        foyerService.delete(foyer);

        verify(foyerRepository, times(1)).delete(foyer);
        logger.info("\u001B[32mFoyer deleted successfully.\u001B[0m");

        logger.info("\u001B[34mEnding testDeleteFoyer...\u001B[0m");
    }

    @Test
    void testAffecterFoyerAUniversite() {
        logger.info("\u001B[34mStarting testAffecterFoyerAUniversite...\u001B[0m");

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite("University 1")).thenReturn(universite);
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite updatedUniversite = foyerService.affecterFoyerAUniversite(1L, "University 1");

        assertThat(updatedUniversite.getFoyer()).isNotNull();
        assertThat(updatedUniversite.getFoyer().getNomFoyer()).isEqualTo("Foyer 1");

        logger.info("\u001B[32mFoyer successfully assigned to university: " + updatedUniversite.getNomUniversite() + "\u001B[0m");

        verify(universiteRepository, times(1)).save(universite);
        logger.info("\u001B[34mEnding testAffecterFoyerAUniversite...\u001B[0m");
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        logger.info("\u001B[34mStarting testDesaffecterFoyerAUniversite...\u001B[0m");

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite updatedUniversite = foyerService.desaffecterFoyerAUniversite(1L);

        assertThat(updatedUniversite.getFoyer()).isNull();
        logger.info("\u001B[32mFoyer successfully disassociated from university.\u001B[0m");

        verify(universiteRepository, times(1)).save(universite);
        logger.info("\u001B[34mEnding testDesaffecterFoyerAUniversite...\u001B[0m");
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() {
        logger.info("\u001B[34mStarting testAjouterFoyerEtAffecterAUniversite...\u001B[0m");

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Foyer addedFoyer = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        assertThat(addedFoyer).isNotNull();
        logger.info("\u001B[32mFoyer added and associated with Universite successfully.\u001B[0m");

        verify(foyerRepository, times(1)).save(foyer);
        verify(universiteRepository, times(1)).save(universite);

        logger.info("\u001B[34mEnding testAjouterFoyerEtAffecterAUniversite...\u001B[0m");
    }

    @Test
    void testAjoutFoyerEtBlocs() {
        logger.info("\u001B[34mStarting testAjoutFoyerEtBlocs...\u001B[0m");

        // Create blocs
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();

        // Create foyer and associate it with the blocs
        Foyer foyer = new Foyer();
        foyer.setBlocs(Arrays.asList(bloc1, bloc2)); // Set blocs for the foyer

        // Mock repository methods
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer); // Return the foyer
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1, bloc2); // Mock bloc saving

        // Call the method to test
        Foyer addedFoyer = foyerService.ajoutFoyerEtBlocs(foyer);

        // Verify that blocRepository.save() was called for each bloc
        verify(blocRepository, times(1)).save(bloc1); // Verify that bloc1 was saved once
        verify(blocRepository, times(1)).save(bloc2); // Verify that bloc2 was saved once

        // Assert that the returned Foyer is not null
        assertThat(addedFoyer).isNotNull();
        logger.info("\u001B[32mFoyer and blocs added successfully.\u001B[0m");

        // Optional: Use ArgumentCaptor to capture the actual objects passed to the save method
        ArgumentCaptor<Bloc> blocCaptor = ArgumentCaptor.forClass(Bloc.class);
        verify(blocRepository, times(2)).save(blocCaptor.capture());
        List<Bloc> capturedBlocs = blocCaptor.getAllValues();
        assertThat(capturedBlocs).containsExactly(bloc1, bloc2);

        logger.info("\u001B[34mEnding testAjoutFoyerEtBlocs...\u001B[0m");
    }
    }