package tn.esprit.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.*;
import tn.esprit.spring.DAO.Repositories.*;
import tn.esprit.spring.Services.Reservation.ReservationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ReservationServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceTest.class);

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    private ReservationService reservationService;

    private Reservation reservation;
    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository, etudiantRepository);

        etudiant = new Etudiant(1L, "John Doe", "CS", null);

        reservation = new Reservation("R1", LocalDate.of(2023, 9, 1), true, Arrays.asList(etudiant));
    }

    @Test
    void testAddOrUpdateReservation() {
        logger.info("\u001B[34mStarting testAddOrUpdateReservation...\u001B[0m");

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.addOrUpdate(reservation);

        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getIdReservation()).isEqualTo("R1");

        logger.info("\u001B[32mReservation saved successfully with ID: " + savedReservation.getIdReservation() + "\u001B[0m");

        verify(reservationRepository, times(1)).save(reservation);
        logger.info("\u001B[34mEnding testAddOrUpdateReservation...\u001B[0m");
    }

    @Test
    void testFindAllReservations() {
        logger.info("\u001B[34mStarting testFindAllReservations...\u001B[0m");

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationService.findAll();

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getIdReservation()).isEqualTo("R1");

        logger.info("\u001B[32mFound reservations: " + reservations.size() + "\u001B[0m");

        verify(reservationRepository, times(1)).findAll();
        logger.info("\u001B[34mEnding testFindAllReservations...\u001B[0m");
    }

    @Test
    void testFindByIdReservation() {
        logger.info("\u001B[34mStarting testFindByIdReservation...\u001B[0m");

        when(reservationRepository.findById("R1")).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.findById("R1");

        assertThat(foundReservation).isNotNull();
        assertThat(foundReservation.getIdReservation()).isEqualTo("R1");

        logger.info("\u001B[32mFound reservation: " + foundReservation.getIdReservation() + "\u001B[0m");

        verify(reservationRepository, times(1)).findById("R1");
        logger.info("\u001B[34mEnding testFindByIdReservation...\u001B[0m");
    }

    @Test
    void testDeleteByIdReservation() {
        logger.info("\u001B[34mStarting testDeleteByIdReservation...\u001B[0m");

        doNothing().when(reservationRepository).deleteById("R1");

        reservationService.deleteById("R1");

        verify(reservationRepository, times(1)).deleteById("R1");
        logger.info("\u001B[32mReservation with ID R1 deleted successfully.\u001B[0m");

        logger.info("\u001B[34mEnding testDeleteByIdReservation...\u001B[0m");
    }

    @Test
    void testDeleteReservation() {
        logger.info("\u001B[34mStarting testDeleteReservation...\u001B[0m");

        doNothing().when(reservationRepository).delete(reservation);

        reservationService.delete(reservation);

        verify(reservationRepository, times(1)).delete(reservation);
        logger.info("\u001B[32mReservation deleted successfully.\u001B[0m");

        logger.info("\u001B[34mEnding testDeleteReservation...\u001B[0m");
    }

    @Test
    void testAffecterEtudiantAReservation() {
        logger.info("\u001B[34mStarting testAffecterEtudiantAReservation...\u001B[0m");

        when(reservationRepository.findById("R1")).thenReturn(Optional.of(reservation));
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.affecterEtudiantAReservation(1L, "R1");

        assertThat(updatedReservation.getEtudiants()).contains(etudiant);

        logger.info("\u001B[32mEtudiant successfully assigned to reservation: " + updatedReservation.getIdReservation() + "\u001B[0m");

        verify(reservationRepository, times(1)).save(reservation);
        logger.info("\u001B[34mEnding testAffecterEtudiantAReservation...\u001B[0m");
    }

    @Test
    void testRetirerEtudiantDeReservation() {
        logger.info("\u001B[34mStarting testRetirerEtudiantDeReservation...\u001B[0m");

        when(reservationRepository.findById("R1")).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.retirerEtudiantDeReservation(1L, "R1");

        assertThat(updatedReservation.getEtudiants()).doesNotContain(etudiant);
        logger.info("\u001B[32mEtudiant successfully disassociated from reservation.\u001B[0m");

        verify(reservationRepository, times(1)).save(reservation);
        logger.info("\u001B[34mEnding testRetirerEtudiantDeReservation...\u001B[0m");
    }
}
