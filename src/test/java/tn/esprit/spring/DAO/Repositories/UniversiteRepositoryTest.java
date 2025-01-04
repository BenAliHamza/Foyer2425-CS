//package tn.esprit.spring.DAO.Repositories;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import tn.esprit.spring.DAO.Entities.*;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//public class UniversiteRepositoryTest {
//
//    @Mock
//    private UniversiteRepository universiteRepository;
//
//    private Universite universite1;
//    private Universite universite2;
//    private Foyer foyer;
//    private Bloc bloc;
//    private Chambre chambre;
//    private Etudiant etudiant1;
//    private Etudiant etudiant2;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialisation des entités pour le test
//
//
//        bloc = new Bloc(1L, "Bloc A", 50, null, Arrays.asList(chambre));
//        foyer = new Foyer(1L, "Foyer Central", 200, null, Arrays.asList(bloc));
//
//        universite1 = new Universite(1L, "Universite Tunis", "Avenue Habib Bourguiba", foyer);
//        universite2 = new Universite(2L, "Universite Sfax", "Avenue de la République", null);
//
//        // Liaison des entités
//
//        chambre.setBloc(bloc);
//        bloc.setFoyer(foyer);
//        foyer.setUniversite(universite1);
//
//        // Simulation des méthodes du repository
//        lenient().when(universiteRepository.findByNomUniversite("Universite Tunis")).thenReturn(universite1);
//
//        lenient().when(universiteRepository.findByFoyerCapaciteFoyerLessThan(150)).thenReturn(Arrays.asList(universite2));
//    }
//
//    @Test
//    public void testFindByNomUniversite() {
//        Universite foundUniversite = universiteRepository.findByNomUniversite("Universite Tunis");
//        assertNotNull(foundUniversite, "Universite should not be null");
//        assertEquals("Universite Tunis", foundUniversite.getNomUniversite(), "Universite name does not match");
//    }
//
//    @Test
//    public void testFindByFoyerBlocsChambresReservationsEtudiants() {
//        List<Universite> universites = universiteRepository.findByFoyerBlocsChambresReservationsEtudiantsNomEtLikeAndFoyerBlocsChambresReservationsEtudiantsDateNaissanceBetween(
//                "Ahmed", LocalDate.of(1998, 1, 1), LocalDate.of(2002, 1, 1)
//        );
//        assertNotNull(universites, "Universites list should not be null");
//        assertEquals(1, universites.size(), "Size of universites list should be 1");
//        assertEquals("Universite Tunis", universites.get(0).getNomUniversite(), "Universite name does not match");
//    }
//
//    @Test
//    public void testFindByFoyerCapaciteFoyerLessThan() {
//        List<Universite> universites = universiteRepository.findByFoyerCapaciteFoyerLessThan(150);
//        assertNotNull(universites, "Universites list should not be null");
//        assertEquals(1, universites.size(), "Size of universites list should be 1");
//        assertEquals("Universite Sfax", universites.get(0).getNomUniversite(), "Universite name does not match");
//    }
//}
