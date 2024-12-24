package tn.esprit.spring.DAO.Repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.TypeChambre;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocRepositoryTest {

    @Mock
    private BlocRepository blocRepository;

    private Bloc bloc1;
    private Bloc bloc2;

    @BeforeEach
    public void setUp() {
        bloc1 = new Bloc(1L, "Bloc 1", 50, null, null);
        bloc2 = new Bloc(2L, "Bloc 2", 70, null, null);

        lenient().when(blocRepository.selectByNomBJPQL1("Bloc 1")).thenReturn(bloc1);
        lenient().when(blocRepository.findByNomBloc("Bloc 2")).thenReturn(bloc2);
        lenient().when(blocRepository.findByCapaciteBlocGreaterThan(60)).thenReturn(Arrays.asList(bloc2));
        lenient().when(blocRepository.getByNomBlocAndCapaciteBloc("Bloc 1", 50)).thenReturn(Arrays.asList(bloc1));
        lenient().when(blocRepository.selectBlocsByTypeChambreJPQL(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(bloc1));
    }

    @Test
    public void testSelectByNomBJPQL1() {
        Bloc foundBloc = blocRepository.selectByNomBJPQL1("Bloc 1");
        assertNotNull(foundBloc, "Bloc should not be null");
        assertEquals("Bloc 1", foundBloc.getNomBloc(), "Bloc name does not match");
    }

    @Test
    public void testFindByNomBloc() {
        Bloc foundBloc = blocRepository.findByNomBloc("Bloc 2");
        assertNotNull(foundBloc, "Bloc should not be null");
        assertEquals("Bloc 2", foundBloc.getNomBloc(), "Bloc name does not match");
    }

    @Test
    public void testFindByCapaciteBlocGreaterThan() {
        List<Bloc> blocs = blocRepository.findByCapaciteBlocGreaterThan(60);
        assertNotNull(blocs, "Blocs list should not be null");
        assertEquals(1, blocs.size(), "Size of blocs list should be 1");
        assertEquals("Bloc 2", blocs.get(0).getNomBloc(), "Bloc name does not match");
    }

    @Test
    public void testGetByNomBlocAndCapaciteBloc() {
        List<Bloc> blocs = blocRepository.getByNomBlocAndCapaciteBloc("Bloc 1", 50);
        assertNotNull(blocs, "Blocs list should not be null");
        assertEquals(1, blocs.size(), "Size of blocs list should be 1");
        assertEquals("Bloc 1", blocs.get(0).getNomBloc(), "Bloc name does not match");
    }

    @Test
    public void testSelectBlocsByTypeChambreJPQL() {
        List<Bloc> blocs = blocRepository.selectBlocsByTypeChambreJPQL(TypeChambre.SIMPLE);
        assertNotNull(blocs, "Blocs list should not be null");
        assertEquals(1, blocs.size(), "Size of blocs list should be 1");
        assertEquals("Bloc 1", blocs.get(0).getNomBloc(), "Bloc name does not match");
    }
}
