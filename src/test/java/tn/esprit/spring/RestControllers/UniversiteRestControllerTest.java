//package tn.esprit.spring.RestControllers;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import tn.esprit.spring.DAO.Entities.Universite;
//import tn.esprit.spring.Services.Universite.IUniversiteService;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class UniversiteRestControllerTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(UniversiteRestControllerTest.class);
//
//    @Mock
//    private IUniversiteService universiteService;
//
//    @InjectMocks
//    private UniversiteRestController universiteRestController;
//
//    private Universite universite1;
//    private Universite universite2;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        universite1 = new Universite(1L, "Universite Tunis", "Avenue Habib Bourguiba", null);
//        universite2 = new Universite(2L, "Universite Sfax", "Avenue de la République", null);
//    }
//
//    @Test
//    public void testAddOrUpdate() {
//        logger.info("\u001B[32mTesting addOrUpdate() method\u001B[0m");
//
//        when(universiteService.addOrUpdate(any(Universite.class))).thenReturn(universite1);
//
//        Universite result = universiteRestController.addOrUpdate(universite1);
//
//        assertNotNull(result);
//        assertEquals("Universite Tunis", result.getNomUniversite());
//    }
//
//    @Test
//    public void testFindAll() {
//        logger.info("\u001B[36mTesting findAll() method\u001B[0m");
//
//        List<Universite> universites = Arrays.asList(universite1, universite2);
//        when(universiteService.findAll()).thenReturn(universites);
//
//        List<Universite> result = universiteRestController.findAll();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertTrue(result.contains(universite1));
//        assertTrue(result.contains(universite2));
//    }
//
//    @Test
//    public void testFindById() {
//        logger.info("\u001B[34mTesting findById() method\u001B[0m");
//
//        when(universiteService.findById(1L)).thenReturn(universite1);
//
//        Universite result = universiteRestController.findById(1L);
//
//        assertNotNull(result);
//        assertEquals("Universite Tunis", result.getNomUniversite());
//    }
//
//    @Test
//    public void testDelete() {
//        logger.info("\u001B[31mTesting delete() method\u001B[0m");
//
//        doNothing().when(universiteService).delete(any(Universite.class));
//
//        universiteRestController.delete(universite1);
//
//        verify(universiteService, times(1)).delete(universite1);
//    }
//
//    @Test
//    public void testDeleteById() {
//        logger.info("\u001B[33mTesting deleteById() method\u001B[0m");
//
//        doNothing().when(universiteService).deleteById(1L);
//
//        universiteRestController.deleteById(1L);
//
//        verify(universiteService, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void testAjouterUniversiteEtSonFoyer() {
//        logger.info("\u001B[35mTesting ajouterUniversiteEtSonFoyer() method\u001B[0m");
//
//        when(universiteService.ajouterUniversiteEtSonFoyer(any(Universite.class))).thenReturn(universite1);
//
//        Universite result = universiteRestController.ajouterUniversiteEtSonFoyer(universite1);
//
//        assertNotNull(result);
//        assertEquals("Universite Tunis", result.getNomUniversite());
//    }
//}
