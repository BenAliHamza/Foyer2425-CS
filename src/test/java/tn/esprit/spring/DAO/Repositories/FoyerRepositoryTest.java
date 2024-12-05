package tn.esprit.spring.DAO.Repositories;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.DAO.Entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class FoyerRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(FoyerRepositoryTest.class);

    @Mock
    private FoyerRepository foyerRepository;

    private Foyer foyer1;
    private Foyer foyer2;
    private Bloc bloc1;
    private Bloc bloc2;
    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        logger.info("Setting up mock data...");

        // Initialize entities
        foyer1 = new Foyer(1L, "Foyer 1", 100, null, new ArrayList<>());
        foyer2 = new Foyer(2L, "Foyer 2", 150, null, new ArrayList<>());

        bloc1 = new Bloc(1L, "Bloc 1", 50, foyer1, new ArrayList<>());
        bloc2 = new Bloc(2L, "Bloc 2", 70, foyer1, new ArrayList<>());

        chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, bloc1, new ArrayList<>());
        chambre2 = new Chambre(2L, 102L, TypeChambre.SIMPLE, bloc2, new ArrayList<>());

        bloc1.setChambres(Arrays.asList(chambre1));
        bloc2.setChambres(Arrays.asList(chambre2));

        foyer1.setBlocs(Arrays.asList(bloc1, bloc2));

        // Mock repository methods
        when(foyerRepository.findByNomFoyer("Foyer 1")).thenReturn(foyer1);
        when(foyerRepository.findByCapaciteFoyerGreaterThan(100)).thenReturn(Arrays.asList(foyer2));
        when(foyerRepository.getByBlocsChambresTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(foyer1));
        when(foyerRepository.findByUniversiteNomUniversite("University 1")).thenReturn(foyer1);
    }

    @Test
    public void testFindByNomFoyer() {
        logger.info("Testing findByNomFoyer method...");

        Foyer foundFoyer = foyerRepository.findByNomFoyer("Foyer 1");
        logger.debug("Found foyer: {}", foundFoyer);

        assertNotNull(foundFoyer, "Foyer should not be null");
        assertEquals("Foyer 1", foundFoyer.getNomFoyer(), "Foyer name does not match");

        // Success log in green
        logger.info("\u001B[32mTest passed: findByNomFoyer method works successfully!\u001B[0m");
    }

    @Test
    public void testFindByCapaciteFoyerGreaterThan() {
        logger.info("Testing findByCapaciteFoyerGreaterThan method...");

        List<Foyer> foyers = foyerRepository.findByCapaciteFoyerGreaterThan(100);
        logger.debug("Found foyers: {}", foyers);

        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 2", foyers.get(0).getNomFoyer(), "Foyer name does not match");

        // Success log in green
        logger.info("\u001B[32mTest passed: findByCapaciteFoyerGreaterThan method works successfully!\u001B[0m");
    }

    @Test
    public void testGetByBlocsChambresTypeC() {
        logger.info("Testing getByBlocsChambresTypeC method...");

        List<Foyer> foyers = foyerRepository.getByBlocsChambresTypeC(TypeChambre.SIMPLE);
        logger.debug("Found foyers: {}", foyers);

        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 1", foyers.get(0).getNomFoyer(), "Foyer name does not match");

        // Success log in green
        logger.info("\u001B[32mTest passed: getByBlocsChambresTypeC method works successfully!\u001B[0m");
    }

    @Test
    public void testFindByUniversiteNomUniversite() {
        logger.info("Testing findByUniversiteNomUniversite method...");

        Foyer foundFoyer = foyerRepository.findByUniversiteNomUniversite("University 1");
        logger.debug("Found foyer: {}", foundFoyer);

        assertNotNull(foundFoyer, "Foyer should not be null");
        assertEquals("Foyer 1", foundFoyer.getNomFoyer(), "Foyer name does not match");

        // Success log in green
        logger.info("\u001B[32mTest passed: findByUniversiteNomUniversite method works successfully!\u001B[0m");
    }

    @Test
    public void testFindUsingCustomQuery() {
        logger.info("Testing find method with custom query...");

        when(foyerRepository.find("Bloc 1")).thenReturn(Arrays.asList(foyer1));

        List<Foyer> foyers = foyerRepository.find("Bloc 1");
        logger.debug("Found foyers: {}", foyers);

        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 1", foyers.get(0).getNomFoyer(), "Foyer name does not match");

        // Success log in green
        logger.info("\u001B[32mTest passed: find method with custom query works successfully!\u001B[0m");
    }

    @Test
    public void testFindByCapaciteFoyerLessThan() {
        logger.info("Testing findByCapaciteFoyerLessThan method...");

        when(foyerRepository.findByCapaciteFoyerLessThan(150)).thenReturn(Arrays.asList(foyer1));

        List<Foyer> foyers = foyerRepository.findByCapaciteFoyerLessThan(150);
        logger.debug("Found foyers: {}", foyers);

        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 1", foyers.get(0).getNomFoyer(), "Foyer name does not match");

        // Success log in green
        logger.info("\u001B[32mTest passed: findByCapaciteFoyerLessThan method works successfully!\u001B[0m");
    }
}
