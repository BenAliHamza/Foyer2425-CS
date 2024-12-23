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
import tn.esprit.spring.Services.Reservation.IReservationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservationRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ReservationRestControllerTest.class);

    @Mock
    private IReservationService reservationService;

    @InjectMocks
    private ReservationRestController reservationRestController;

    private Reservation reservation;
    private Etudiant etudiant;

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

        when(reservationService.addOrUpdate(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationRestController.addOrUpdate(reservation);

        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
        assertTrue(result.isEstValide());
    }

    @Test
    public void testFindAll() {
        logger.info("\u001B[36mTesting findAll() method\u001B[0m");

        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationService.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationRestController.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(reservation));
    }

    @Test
    public void testFindById() {
        logger.info("\u001B[34mTesting findById() method\u001B[0m");

        when(reservationService.findById("R1")).thenReturn(reservation);

        Reservation result = reservationRestController.findById("R1");

        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
    }

    @Test
    public void testDelete() {
        logger.info("\u001B[31mTesting delete() method\u001B[0m");

        doNothing().when(reservationService).delete(any(Reservation.class));

        reservationRestController.delete(reservation);

        verify(reservationService, times(1)).delete(reservation);
    }

    @Test
    public void testDeleteById() {
        logger.info("\u001B[33mTesting deleteById() method\u001B[0m");

        doNothing().when(reservationService).deleteById("R1");

        reservationRestController.deleteById("R1");

        verify(reservationService, times(1)).deleteById("R1");
    }

    @Test
    public void testAffecterEtudiantAReservation() {
        logger.info("\u001B[35mTesting affecterEtudiantAReservation() method\u001B[0m");

        when(reservationService.affecterEtudiantAReservation(1L, "R1")).thenReturn(etudiant);

        Etudiant result = reservationRestController.affecterEtudiantAReservation(1L, "R1");

        assertNotNull(result);
        assertEquals("John", result.getNomEt());
    }

    @Test
    public void testRetirerEtudiantDeReservation() {
        logger.info("\u001B[37mTesting retirerEtudiantDeReservation() method\u001B[0m");

        when(reservationService.retirerEtudiantDeReservation(1L, "R1")).thenReturn(etudiant);

        Etudiant result = reservationRestController.retirerEtudiantDeReservation(1L, "R1");

        assertNotNull(result);
        assertEquals("John", result.getNomEt());
    }
}
