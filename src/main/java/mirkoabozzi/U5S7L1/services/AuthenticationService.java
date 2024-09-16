package mirkoabozzi.U5S7L1.services;

import mirkoabozzi.U5S7L1.dto.EmployeeLoginDTO;
import mirkoabozzi.U5S7L1.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private EmployeesService employeesService; //importiamo il service dei dipendenti


    public String checkCredentialAndCreateToken(EmployeeLoginDTO payload) {
        Employee found = this.employeesService.findByEmail(payload.email());
        if (found.getPassword().equals(payload.password())) {

        }

    }
}
