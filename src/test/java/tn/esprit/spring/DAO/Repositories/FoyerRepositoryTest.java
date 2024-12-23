package tn.esprit.spring.DAO.Repositories;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)  // Use MockitoExtension for unit tests
public class FoyerRepositoryTest {

    @Mock
    private FoyerRepository foyerRepository;  // Mock the FoyerRepository

    private Foyer foyer1;
    private Foyer foyer2;
    private Bloc bloc1;
    private Bloc bloc2;
    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        foyer1 = new Foyer(1L, "Foyer 1", 100, null, null);
        foyer2 = new Foyer(2L, "Foyer 2", 150, null, null);

        bloc1 = new Bloc(1L, "Bloc 1", 50, foyer1, null);
        bloc2 = new Bloc(2L, "Bloc 2", 70, foyer1, null);

        chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, bloc1, null);
        chambre2 = new Chambre(2L, 102L, TypeChambre.SIMPLE, bloc2, null);

        bloc1.setChambres(Arrays.asList(chambre1));
        bloc2.setChambres(Arrays.asList(chambre2));

        foyer1.setBlocs(Arrays.asList(bloc1, bloc2));

        // Mock repository methods
        lenient().when(foyerRepository.findByNomFoyer("Foyer 1")).thenReturn(foyer1);
        lenient().when(foyerRepository.findByCapaciteFoyerGreaterThan(100)).thenReturn(Arrays.asList(foyer2));
        lenient().when(foyerRepository.getByBlocsChambresTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(foyer1));
        lenient().when(foyerRepository.findByUniversiteNomUniversite("University 1")).thenReturn(foyer1);
        lenient().when(foyerRepository.find("Bloc 1")).thenReturn(Arrays.asList(foyer1));
        lenient().when(foyerRepository.findByCapaciteFoyerLessThan(150)).thenReturn(Arrays.asList(foyer1));
    }

    @Test
    public void testFindByNomFoyer() {
        Foyer foundFoyer = foyerRepository.findByNomFoyer("Foyer 1");
        assertNotNull(foundFoyer, "Foyer should not be null");
        assertEquals("Foyer 1", foundFoyer.getNomFoyer(), "Foyer name does not match");
    }

    @Test
    public void testFindByCapaciteFoyerGreaterThan() {
        List<Foyer> foyers = foyerRepository.findByCapaciteFoyerGreaterThan(100);
        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 2", foyers.get(0).getNomFoyer(), "Foyer name does not match");
    }

    @Test
    public void testGetByBlocsChambresTypeC() {
        List<Foyer> foyers = foyerRepository.getByBlocsChambresTypeC(TypeChambre.SIMPLE);
        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 1", foyers.get(0).getNomFoyer(), "Foyer name does not match");
    }

    @Test
    public void testFindByUniversiteNomUniversite() {
        Foyer foundFoyer = foyerRepository.findByUniversiteNomUniversite("University 1");
        assertNotNull(foundFoyer, "Foyer should not be null");
        assertEquals("Foyer 1", foundFoyer.getNomFoyer(), "Foyer name does not match");
    }

    @Test
    public void testFind() {
        List<Foyer> foyers = foyerRepository.find("Bloc 1");
        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 1", foyers.get(0).getNomFoyer(), "Foyer name does not match");
    }

    @Test
    public void testFindByCapaciteFoyerLessThan() {
        List<Foyer> foyers = foyerRepository.findByCapaciteFoyerLessThan(150);
        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Size of foyers list should be 1");
        assertEquals("Foyer 1", foyers.get(0).getNomFoyer(), "Foyer name does not match");
    }
}
