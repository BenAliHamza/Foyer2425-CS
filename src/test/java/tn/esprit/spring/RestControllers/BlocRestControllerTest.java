package tn.esprit.spring.RestControllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.Services.Bloc.IBlocService;

import java.util.Arrays;
import java.util.List;

public class BlocRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BlocRestControllerTest.class);

    @Mock
    private IBlocService blocService;

    @InjectMocks
    private BlocRestController blocRestController;

    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bloc = new Bloc(1L, "Bloc A", 100, null, null);
    }

    @Test
    public void testAddOrUpdate() {
        logger.info("Testing addOrUpdate() method");

        when(blocService.addOrUpdate(any(Bloc.class))).thenReturn(bloc);

        Bloc result = blocRestController.addOrUpdate(bloc);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        assertEquals(100, result.getCapaciteBloc());
    }

    @Test
    public void testFindAll() {
        logger.info("Testing findAll() method");

        List<Bloc> blocs = Arrays.asList(bloc);
        when(blocService.findAll()).thenReturn(blocs);

        List<Bloc> result = blocRestController.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(bloc));
    }

    @Test
    public void testFindById() {
        logger.info("Testing findById() method");

        when(blocService.findById(1L)).thenReturn(bloc);

        Bloc result = blocRestController.findById(1L);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testDelete() {
        logger.info("Testing delete() method");

        doNothing().when(blocService).delete(any(Bloc.class));

        blocRestController.delete(bloc);

        verify(blocService, times(1)).delete(bloc);
    }

    @Test
    public void testDeleteById() {
        logger.info("Testing deleteById() method");

        doNothing().when(blocService).deleteById(1L);

        blocRestController.deleteById(1L);

        verify(blocService, times(1)).deleteById(1L);
    }

    @Test
    public void testAffecterChambresABloc() {
        logger.info("Testing affecterChambresABloc() method");

        List<Long> chambres = Arrays.asList(101L, 102L);
        when(blocService.affecterChambresABloc(chambres, "Bloc A")).thenReturn(bloc);

        Bloc result = blocRestController.affecterChambresABloc(chambres, "Bloc A");

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testAffecterBlocAFoyer() {
        logger.info("Testing affecterBlocAFoyer() method");

        when(blocService.affecterBlocAFoyer("Bloc A", "Foyer 1")).thenReturn(bloc);

        Bloc result = blocRestController.affecterBlocAFoyer("Bloc A", "Foyer 1");

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testAffecterBlocAFoyer2() {
        logger.info("Testing affecterBlocAFoyer2() method");

        when(blocService.affecterBlocAFoyer("Bloc A", "Foyer 1")).thenReturn(bloc);

        Bloc result = blocRestController.affecterBlocAFoyer2("Bloc A", "Foyer 1");

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testAjouterBlocEtSesChambres() {
        logger.info("Testing ajouterBlocEtSesChambres() method");

        when(blocService.ajouterBlocEtSesChambres(any(Bloc.class))).thenReturn(bloc);

        Bloc result = blocRestController.ajouterBlocEtSesChambres(bloc);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testAjouterBlocEtAffecterAFoyer() {
        logger.info("Testing ajouterBlocEtAffecterAFoyer() method");

        when(blocService.ajouterBlocEtAffecterAFoyer(any(Bloc.class), eq("Foyer 1"))).thenReturn(bloc);

        Bloc result = blocRestController.ajouterBlocEtAffecterAFoyer(bloc, "Foyer 1");

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }
}
