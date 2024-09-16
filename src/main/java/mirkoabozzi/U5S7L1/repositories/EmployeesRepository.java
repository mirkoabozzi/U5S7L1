package mirkoabozzi.U5S7L1.repositories;

import mirkoabozzi.U5S7L1.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);
}
