package tn.esprit.spring.DAO.Entities;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoyerTest {

    private static final Logger logger = LoggerFactory.getLogger(FoyerTest.class);

    private Foyer foyer;
    private Universite universite;
    private Bloc bloc1;
    private Bloc bloc2;

    @BeforeEach
    public void setUp() {
        // Setup the Universite entity with its constructor
        universite = new Universite(1L, "University 1", "Location 1", null);

        // Setup the Foyer entity with its constructor
        foyer = new Foyer(1L, "Foyer 1", 200, universite, new ArrayList<>());

        // Setup the Blocs for the Foyer
        bloc1 = new Bloc(1L, "Bloc 1", 50, foyer, new ArrayList<>());
        bloc2 = new Bloc(2L, "Bloc 2", 70, foyer, new ArrayList<>());

        // Link the Foyer with Blocs
        foyer.setBlocs(Arrays.asList(bloc1, bloc2));

        // Link the Universite with the Foyer
        universite.setFoyer(foyer);
    }

    @Test
    public void testFoyerProperties() {
        logger.info("\u001B[34mTesting Foyer properties\u001B[0m"); // Blue color

        assertNotNull(foyer);
        assertEquals("Foyer 1", foyer.getNomFoyer());
        assertEquals(200, foyer.getCapaciteFoyer());
    }

    @Test
    public void testAssociationsWithBloc() {
        logger.info("\u001B[32mTesting Foyer-Bloc associations\u001B[0m"); // Green color

        assertNotNull(foyer.getBlocs());
        assertEquals(2, foyer.getBlocs().size());
        assertTrue(foyer.getBlocs().contains(bloc1));
        assertTrue(foyer.getBlocs().contains(bloc2));
    }

    @Test
    public void testUniversiteAssociation() {
        logger.info("\u001B[36mTesting Foyer-Universite association\u001B[0m"); // Cyan color

        Universite foundUniversite = foyer.getUniversite();
        assertNotNull(foundUniversite);
        assertEquals("University 1", foundUniversite.getNomUniversite());
    }

    @Test
    public void testFoyerBuilderPattern() {
        logger.info("\u001B[35mTesting Foyer Builder pattern\u001B[0m"); // Magenta color

        Foyer foyerFromBuilder = Foyer.builder()
                .idFoyer(2L)
                .nomFoyer("Foyer 2")
                .capaciteFoyer(300)
                .universite(universite)
                .blocs(Arrays.asList(bloc1, bloc2))
                .build();

        assertNotNull(foyerFromBuilder);
        assertEquals("Foyer 2", foyerFromBuilder.getNomFoyer());
        assertEquals(300, foyerFromBuilder.getCapaciteFoyer());
    }

    @Test
    public void testFoyerSettersAndGetters() {
        logger.info("\u001B[33mTesting Foyer Setters and Getters\u001B[0m"); // Yellow color

        foyer.setNomFoyer("Foyer Updated");
        foyer.setCapaciteFoyer(250);

        assertEquals("Foyer Updated", foyer.getNomFoyer());
        assertEquals(250, foyer.getCapaciteFoyer());
    }

    @Test
    public void testFoyerToString() {
        logger.info("\u001B[31mTesting Foyer toString method\u001B[0m"); // Red color

        String foyerString = foyer.toString();
        logger.debug("Foyer toString: {}", foyerString);

        assertTrue(foyerString.contains("Foyer 1"));
        assertTrue(foyerString.contains("200"));
    }

    @Test
    public void testUniversiteConstructor() {
        logger.info("\u001B[32mTesting Universite constructor\u001B[0m"); // Green color

        Universite universite = new Universite(2L, "University 2", "Location 2", null);
        assertNotNull(universite);
        assertEquals("University 2", universite.getNomUniversite());
        assertEquals("Location 2", universite.getAdresse());
    }

    @Test
    public void testFoyerUniversiteAssociation() {
        logger.info("\u001B[34mTesting Universite and Foyer relationship\u001B[0m"); // Blue color

        assertNotNull(universite.getFoyer());
        assertEquals(1L, universite.getFoyer().getIdFoyer());
    }
}
