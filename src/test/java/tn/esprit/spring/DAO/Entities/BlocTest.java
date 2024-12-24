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

public class BlocTest {

    private static final Logger logger = LoggerFactory.getLogger(BlocTest.class);

    private Bloc bloc;
    private Foyer foyer;
    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        // Setup the Foyer entity with its constructor
        foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer 1")
                .capaciteFoyer(200)
                .blocs(new ArrayList<>())
                .build();

        // Setup the Bloc entity with its constructor
        bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc 1")
                .capaciteBloc(50)
                .foyer(foyer)
                .chambres(new ArrayList<>())
                .build();

        // Link the Foyer with Bloc
        foyer.getBlocs().add(bloc);

        // Setup the Chambres for the Bloc
        chambre1 = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101)
                .typeC(TypeChambre.SIMPLE)
                .bloc(bloc)
                .reservations(new ArrayList<>())
                .build();

        chambre2 = Chambre.builder()
                .idChambre(2L)
                .numeroChambre(102)
                .typeC(TypeChambre.DOUBLE)
                .bloc(bloc)
                .reservations(new ArrayList<>())
                .build();

        // Link the Bloc with Chambres
        bloc.setChambres(Arrays.asList(chambre1, chambre2));
    }

    @Test
    public void testBlocProperties() {
        logger.info("\u001B[34mTesting Bloc properties\u001B[0m"); // Blue color

        assertNotNull(bloc);
        assertEquals("Bloc 1", bloc.getNomBloc());
        assertEquals(50, bloc.getCapaciteBloc());
    }

    @Test
    public void testAssociationsWithChambres() {
        logger.info("\u001B[32mTesting Bloc-Chambre associations\u001B[0m"); // Green color

        assertNotNull(bloc.getChambres());
        assertEquals(2, bloc.getChambres().size());
        assertTrue(bloc.getChambres().contains(chambre1));
        assertTrue(bloc.getChambres().contains(chambre2));
    }

    @Test
    public void testBlocFoyerAssociation() {
        logger.info("\u001B[36mTesting Bloc-Foyer association\u001B[0m"); // Cyan color

        Foyer foundFoyer = bloc.getFoyer();
        assertNotNull(foundFoyer);
        assertEquals("Foyer 1", foundFoyer.getNomFoyer());
    }

    @Test
    public void testBlocBuilderPattern() {
        logger.info("\u001B[35mTesting Bloc Builder pattern\u001B[0m"); // Magenta color

        Bloc blocFromBuilder = Bloc.builder()
                .idBloc(2L)
                .nomBloc("Bloc 2")
                .capaciteBloc(70)
                .foyer(foyer)
                .chambres(Arrays.asList(chambre1, chambre2))
                .build();

        assertNotNull(blocFromBuilder);
        assertEquals("Bloc 2", blocFromBuilder.getNomBloc());
        assertEquals(70, blocFromBuilder.getCapaciteBloc());
    }

    @Test
    public void testBlocSettersAndGetters() {
        logger.info("\u001B[33mTesting Bloc Setters and Getters\u001B[0m"); // Yellow color

        bloc.setNomBloc("Bloc Updated");
        bloc.setCapaciteBloc(60);

        assertEquals("Bloc Updated", bloc.getNomBloc());
        assertEquals(60, bloc.getCapaciteBloc());
    }


}
