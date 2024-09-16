package mirkoabozzi.U5S7L1.services;

import mirkoabozzi.U5S7L1.dto.EmployeeLoginDTO;
import mirkoabozzi.U5S7L1.entities.Employee;
import mirkoabozzi.U5S7L1.exceptions.UnauthorizedException;
import mirkoabozzi.U5S7L1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private EmployeesService employeesService; //importiamo il service dei dipendenti
    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialAndCreateToken(EmployeeLoginDTO payload) {
        Employee found = this.employeesService.findByEmail(payload.email());
        if (found.getPassword().equals(payload.password())) {
            return this.jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("Incorrect credentials");
        }
    }
}
