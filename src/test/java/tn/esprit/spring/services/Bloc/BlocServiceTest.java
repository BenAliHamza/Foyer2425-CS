package tn.esprit.spring.services.Bloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlocServiceTest {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    private Bloc bloc;
    private Chambre chambre;
    private Foyer foyer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        foyer = new Foyer(1L, "Foyer A", 100, null, null);
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .bloc(null) // Initially not assigned to any bloc
                .reservations(new ArrayList<>())
                .build();
        bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .capaciteBloc(50)
                .foyer(foyer)
                .chambres(new ArrayList<>(Arrays.asList(chambre)))
                .build();
        chambre.setBloc(bloc); // Link chambre to bloc
    }

    @Test
    public void testAddOrUpdate() {
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Bloc result = blocService.addOrUpdate(bloc);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(chambreRepository, times(1)).save(chambre);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testFindAll() {
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc));

        List<Bloc> result = blocService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
    }

    @Test
    public void testFindById() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc result = blocService.findById(1L);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testDeleteById() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        blocService.deleteById(1L);

        verify(chambreRepository, times(1)).deleteAll(bloc.getChambres());
        verify(blocRepository, times(1)).delete(bloc);
    }

    @Test
    public void testAffecterChambresABloc() {
        // Initialize bloc with an empty chambres list
        Bloc bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .chambres(new ArrayList<>())
                .build();

        Chambre chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .bloc(null)
                .reservations(new ArrayList<>())
                .build();

        Chambre chambre2 = Chambre.builder()
                .idChambre(2L)
                .numeroChambre(102L)
                .bloc(null)
                .reservations(new ArrayList<>())
                .build();

        List<Long> chambreNums = Arrays.asList(101L, 102L);

        // Mock repository behavior
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);
        when(chambreRepository.findByNumeroChambre(102L)).thenReturn(chambre2);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre, chambre2);

        // Call the method
        Bloc result = blocService.affecterChambresABloc(chambreNums, "Bloc A");

        // Assert results
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        assertEquals(2, result.getChambres().size());  // Ensure two chambres are assigned
        verify(chambreRepository, times(2)).save(any(Chambre.class));  // Ensure each chambre is saved
    }



    @Test
    public void testAffecterBlocAFoyer() {
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer("Foyer A")).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc result = blocService.affecterBlocAFoyer("Bloc A", "Foyer A");

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        assertEquals(foyer, result.getFoyer());
        verify(blocRepository, times(1)).save(bloc);
    }
}
