package tn.esprit.spring.DAO.Repositories;

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

        import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
        import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.*;

        import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)  // Use MockitoExtension for unit tests
public class ReservationRepositoryTest {

    @Mock
    private ReservationRepository reservationRepository;  // Mock the ReservationRepository

    private Reservation reservation1;
    private Reservation reservation2;
    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        etudiant1 = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Smith")
                .prenomEt("John")
                .cin(12345678L)
                .ecole("Ecole 1")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .build();

        etudiant2 = Etudiant.builder()
                .idEtudiant(2L)
                .nomEt("Doe")
                .prenomEt("Jane")
                .cin(87654321L)
                .ecole("Ecole 2")
                .dateNaissance(LocalDate.of(1998, 5, 15))
                .build();

        reservation1 = Reservation.builder()
                .idReservation("RES1")
                .anneeUniversitaire(LocalDate.of(2023, 1, 1))
                .estValide(true)
                .etudiants(Arrays.asList(etudiant1))
                .build();

        reservation2 = Reservation.builder()
                .idReservation("RES2")
                .anneeUniversitaire(LocalDate.of(2024, 1, 1))
                .estValide(false)
                .etudiants(Arrays.asList(etudiant2))
                .build();

        etudiant1.setReservations(Arrays.asList(reservation1));
        etudiant2.setReservations(Arrays.asList(reservation2));

        // Mock repository methods
        lenient().when(reservationRepository.findByIdReservation("RES1")).thenReturn(reservation1);
        lenient().when(reservationRepository.findByAnneeUniversitaire(LocalDate.of(2023, 1, 1)))
                .thenReturn(Arrays.asList(reservation1));
        lenient().when(reservationRepository.findByEstValide(true)).thenReturn(Arrays.asList(reservation1));
        lenient().when(reservationRepository.findByEtudiantsNomEt("Smith")).thenReturn(Arrays.asList(reservation1));
    }

    @Test
    public void testFindByIdReservation() {
        Reservation foundReservation = reservationRepository.findByIdReservation("RES1");
        assertNotNull(foundReservation, "Reservation should not be null");
        assertEquals("RES1", foundReservation.getIdReservation(), "Reservation ID does not match");
    }

    @Test
    public void testFindByAnneeUniversitaire() {
        List<Reservation> reservations = reservationRepository.findByAnneeUniversitaire(LocalDate.of(2023, 1, 1));
        assertNotNull(reservations, "Reservations list should not be null");
        assertEquals(1, reservations.size(), "Size of reservations list should be 1");
        assertEquals("RES1", reservations.get(0).getIdReservation(), "Reservation ID does not match");
    }

    @Test
    public void testFindByEstValide() {
        List<Reservation> reservations = reservationRepository.findByEstValide(true);
        assertNotNull(reservations, "Reservations list should not be null");
        assertEquals(1, reservations.size(), "Size of reservations list should be 1");
        assertTrue(reservations.get(0).isEstValide(), "Reservation validity does not match");
    }

    @Test
    public void testFindByEtudiantsNomEt() {
        List<Reservation> reservations = reservationRepository.findByEtudiantsNomEt("Smith");
        assertNotNull(reservations, "Reservations list should not be null");
        assertEquals(1, reservations.size(), "Size of reservations list should be 1");
        assertEquals("RES1", reservations.get(0).getIdReservation(), "Reservation ID does not match");
    }
}
