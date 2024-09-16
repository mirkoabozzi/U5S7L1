package mirkoabozzi.U5S7L1.controllers;

import mirkoabozzi.U5S7L1.dto.EmployeeLoginDTO;
import mirkoabozzi.U5S7L1.dto.EmployeeLoginRespDTO;
import mirkoabozzi.U5S7L1.exceptions.BadRequestException;
import mirkoabozzi.U5S7L1.services.AuthenticationService;
import mirkoabozzi.U5S7L1.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authentication") //endpoint predefinito per le autenticazioni
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private EmployeesService employeesService;

    @PostMapping("/login") //endpoint dedicati al login
    public EmployeeLoginRespDTO login(@RequestBody @Validated EmployeeLoginDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return new EmployeeLoginRespDTO(this.authenticationService.checkCredentialAndCreateToken(payload));
        }
    }

}
