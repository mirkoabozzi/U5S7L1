package mirkoabozzi.U5S7L1.repositories;

import mirkoabozzi.U5S7L1.entities.Employee;
import mirkoabozzi.U5S7L1.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, UUID> {

    boolean existsByEmployeeAndTripDate(Employee employee, LocalDate date);
}
