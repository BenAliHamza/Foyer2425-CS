package tn.esprit.spring.RestControllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.Services.Etudiant.IEtudiantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EtudiantRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(EtudiantRestControllerTest.class);

    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    private Etudiant etudiant;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("John")
                .prenomEt("Doe")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .build();

        reservation = Reservation.builder()
                .idReservation("R1")
                .anneeUniversitaire(LocalDate.of(2023, 9, 1))
                .estValide(true)
                .etudiants(Arrays.asList(etudiant))
                .build();
    }

    @Test
    public void testAddOrUpdate() {
        logger.info("\u001B[32mTesting addOrUpdate() method\u001B[0m");

        when(etudiantService.addOrUpdate(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant result = etudiantRestController.addOrUpdate(etudiant);

        assertNotNull(result);
        assertEquals("John", result.getNomEt());
        assertEquals("Doe", result.getPrenomEt());
    }

    @Test
    public void testFindAll() {
        logger.info("\u001B[36mTesting findAll() method\u001B[0m");

        List<Etudiant> etudiants = Arrays.asList(etudiant);
        when(etudiantService.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantRestController.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(etudiant));
    }

    @Test
    public void testFindById() {
        logger.info("\u001B[34mTesting findById() method\u001B[0m");

        when(etudiantService.findById(1L)).thenReturn(etudiant);

        Etudiant result = etudiantRestController.findById(1L);

        assertNotNull(result);
        assertEquals("John", result.getNomEt());
    }

    @Test
    public void testDelete() {
        logger.info("\u001B[31mTesting delete() method\u001B[0m");

        doNothing().when(etudiantService).delete(any(Etudiant.class));

        etudiantRestController.delete(etudiant);

        verify(etudiantService, times(1)).delete(etudiant);
    }

    @Test
    public void testDeleteById() {
        logger.info("\u001B[33mTesting deleteById() method\u001B[0m");

        doNothing().when(etudiantService).deleteById(1L);

        etudiantRestController.deleteById(1L);

        verify(etudiantService, times(1)).deleteById(1L);
    }

    @Test
    public void testAffecterReservationAEtudiant() {
        logger.info("\u001B[35mTesting affecterReservationAEtudiant() method\u001B[0m");

        when(etudiantService.affecterReservationAEtudiant(1L, "R1")).thenReturn(reservation);

        Reservation result = etudiantRestController.affecterReservationAEtudiant(1L, "R1");

        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
    }

    @Test
    public void testRetirerReservationDeEtudiant() {
        logger.info("\u001B[37mTesting retirerReservationDeEtudiant() method\u001B[0m");

        when(etudiantService.retirerReservationDeEtudiant(1L, "R1")).thenReturn(reservation);

        Reservation result = etudiantRestController.retirerReservationDeEtudiant(1L, "R1");

        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
    }
}

