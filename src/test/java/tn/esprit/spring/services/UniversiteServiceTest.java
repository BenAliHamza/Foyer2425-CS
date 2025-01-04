//package tn.esprit.spring.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import tn.esprit.spring.DAO.Entities.Universite;
//import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
//import tn.esprit.spring.Services.Universite.UniversiteService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.assertj.core.api.Assertions.*;
//
//class UniversiteServiceTest {
//
//    @Mock
//    private UniversiteRepository universiteRepository;
//
//    @InjectMocks
//    private UniversiteService universiteService;
//
//    private Universite universite;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        universite = new Universite(1L, "University 1", "Location 1", null);
//    }
//
//    @Test
//    void testAddOrUpdate() {
//        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);
//
//        Universite savedUniversite = universiteService.addOrUpdate(universite);
//
//        assertThat(savedUniversite).isNotNull();
//        assertThat(savedUniversite.getNomUniversite()).isEqualTo("University 1");
//
//        verify(universiteRepository, times(1)).save(universite);
//    }
//
//    @Test
//    void testFindAll() {
//        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite));
//
//        List<Universite> universites = universiteService.findAll();
//
//        assertThat(universites).hasSize(1);
//        assertThat(universites.get(0).getNomUniversite()).isEqualTo("University 1");
//
//        verify(universiteRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testFindById() {
//        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
//
//        Universite foundUniversite = universiteService.findById(1L);
//
//        assertThat(foundUniversite).isNotNull();
//        assertThat(foundUniversite.getNomUniversite()).isEqualTo("University 1");
//
//        verify(universiteRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testDeleteById() {
//        doNothing().when(universiteRepository).deleteById(1L);
//
//        universiteService.deleteById(1L);
//
//        verify(universiteRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDelete() {
//        doNothing().when(universiteRepository).delete(universite);
//
//        universiteService.delete(universite);
//
//        verify(universiteRepository, times(1)).delete(universite);
//    }
//
//    @Test
//    void testAjouterUniversiteEtSonFoyer() {
//        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);
//
//        Universite savedUniversite = universiteService.ajouterUniversiteEtSonFoyer(universite);
//
//        assertThat(savedUniversite).isNotNull();
//        assertThat(savedUniversite.getNomUniversite()).isEqualTo("University 1");
//
//        verify(universiteRepository, times(1)).save(universite);
//    }
//}
//
