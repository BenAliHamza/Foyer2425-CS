//package tn.esprit.spring.DAO.Entities;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class UniversiteTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(UniversiteTest.class);
//
//    private Universite universite;
//    private Foyer foyer;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialisation des objets pour le test
//        foyer = new Foyer(1L, "Foyer Universitaire", 300, null, null);
//        universite = new Universite(1L, "Universite Tunis", "Rue de l'Université", foyer);
//
//        // Liaison entre Universite et Foyer
//        foyer.setUniversite(universite);
//    }
//
//    @Test
//    public void testUniversiteProperties() {
//        logger.info("Testing Universite properties");
//
//        assertNotNull(universite);
//        assertEquals(1L, universite.getIdUniversite());
//        assertEquals("Universite Tunis", universite.getNomUniversite());
//        assertEquals("Rue de l'Université", universite.getAdresse());
//    }
//
//    @Test
//    public void testUniversiteAndFoyerAssociation() {
//        logger.info("Testing Universite-Foyer association");
//
//        assertNotNull(universite.getFoyer());
//        assertEquals("Foyer Universitaire", universite.getFoyer().getNomFoyer());
//        assertEquals(300, universite.getFoyer().getCapaciteFoyer());
//    }
//
//    @Test
//    public void testUniversiteConstructor() {
//        logger.info("Testing Universite constructor");
//
//        Universite newUniversite = new Universite(2L, "Universite Carthage", "Avenue Carthage", null);
//        assertNotNull(newUniversite);
//        assertEquals("Universite Carthage", newUniversite.getNomUniversite());
//        assertEquals("Avenue Carthage", newUniversite.getAdresse());
//    }
//
//    @Test
//    public void testUniversiteSettersAndGetters() {
//        logger.info("Testing Universite setters and getters");
//
//        universite.setNomUniversite("Nouvelle Universite");
//        universite.setAdresse("Nouvelle Adresse");
//
//        assertEquals("Nouvelle Universite", universite.getNomUniversite());
//        assertEquals("Nouvelle Adresse", universite.getAdresse());
//    }
//
//    @Test
//    public void testUniversiteToString() {
//        logger.info("Testing Universite toString method");
//
//        String universiteString = universite.toString();
//        logger.debug("Universite toString: {}", universiteString);
//
//        assertTrue(universiteString.contains("Universite Tunis"));
//        assertTrue(universiteString.contains("Rue de l'Université"));
//    }
//}
