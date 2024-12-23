package tn.esprit.spring.RestControllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.Services.Foyer.IFoyerService;

import java.util.Arrays;
import java.util.List;

public class FoyerRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(FoyerRestControllerTest.class);

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    private Foyer foyer;
    private Universite universite;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        foyer = new Foyer(1L, "Foyer 1", 200, null, null);
        universite = new Universite(1L, "University 1", "Location 1", null);
    }

    @Test
    public void testAddOrUpdate() {
        logger.info("\u001B[32mTesting addOrUpdate() method\u001B[0m");

        when(foyerService.addOrUpdate(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerRestController.addOrUpdate(foyer);

        assertNotNull(result);
        assertEquals("Foyer 1", result.getNomFoyer());
        assertEquals(200, result.getCapaciteFoyer());
    }

    @Test
    public void testFindAll() {
        logger.info("\u001B[36mTesting findAll() method\u001B[0m");

        List<Foyer> foyers = Arrays.asList(foyer);
        when(foyerService.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerRestController.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(foyer));
    }

    @Test
    public void testFindById() {
        logger.info("\u001B[34mTesting findById() method\u001B[0m");

        when(foyerService.findById(1L)).thenReturn(foyer);

        Foyer result = foyerRestController.findById(1L);

        assertNotNull(result);
        assertEquals("Foyer 1", result.getNomFoyer());
    }

    @Test
    public void testDelete() {
        logger.info("\u001B[31mTesting delete() method\u001B[0m");

        doNothing().when(foyerService).delete(any(Foyer.class));

        foyerRestController.delete(foyer);

        verify(foyerService, times(1)).delete(foyer);
    }

    @Test
    public void testDeleteById() {
        logger.info("\u001B[33mTesting deleteById() method\u001B[0m");

        doNothing().when(foyerService).deleteById(1L);

        foyerRestController.deleteById(1L);

        verify(foyerService, times(1)).deleteById(1L);
    }

    @Test
    public void testAffecterFoyerAUniversite() {
        logger.info("\u001B[35mTesting affecterFoyerAUniversite() method\u001B[0m");

        when(foyerService.affecterFoyerAUniversite(1L, "University 1")).thenReturn(universite);

        Universite result = foyerRestController.affecterFoyerAUniversite(1L, "University 1");

        assertNotNull(result);
        assertEquals("University 1", result.getNomUniversite());
    }

    @Test
    public void testDesaffecterFoyerAUniversite() {
        logger.info("\u001B[37mTesting desaffecterFoyerAUniversite() method\u001B[0m");

        when(foyerService.desaffecterFoyerAUniversite(1L)).thenReturn(universite);

        Universite result = foyerRestController.desaffecterFoyerAUniversite(1L);

        assertNotNull(result);
        assertEquals("University 1", result.getNomUniversite());
    }

    @Test
    public void testAjouterFoyerEtAffecterAUniversite() {
        logger.info("\u001B[32mTesting ajouterFoyerEtAffecterAUniversite() method\u001B[0m");

        when(foyerService.ajouterFoyerEtAffecterAUniversite(any(Foyer.class), eq(1L))).thenReturn(foyer);

        Foyer result = foyerRestController.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        assertNotNull(result);
        assertEquals("Foyer 1", result.getNomFoyer());
        assertEquals(200, result.getCapaciteFoyer());
    }

    @Test
    public void testAffecterFoyerAUniversiteByPathVariable() {
        logger.info("\u001B[35mTesting affecterFoyerAUniversite() with path variable\u001B[0m");

        when(foyerService.affecterFoyerAUniversite(1L, 1L)).thenReturn(universite);

        Universite result = foyerRestController.affecterFoyerAUniversite(1L, 1L);

        assertNotNull(result);
        assertEquals("University 1", result.getNomUniversite());
    }
}
