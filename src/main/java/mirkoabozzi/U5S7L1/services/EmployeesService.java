package mirkoabozzi.U5S7L1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import mirkoabozzi.U5S7L1.dto.EmployeesDTO;
import mirkoabozzi.U5S7L1.entities.Employee;
import mirkoabozzi.U5S7L1.exceptions.BadRequestException;
import mirkoabozzi.U5S7L1.exceptions.NotFoundException;
import mirkoabozzi.U5S7L1.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeesService {
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private JavaMailSender javaMailSender;

    //POST
    public Employee save(EmployeesDTO payload) {
        if (employeesRepository.existsByEmail(payload.email()))
            throw new BadRequestException("Email " + payload.email() + " already on DB");
        Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email(), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname(), payload.password());

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(payload.email());
        msg.setSubject("Welcome to JAVA");
        msg.setText("Hi " + payload.name() + " " + payload.surname() + " this mail was sent from JAVA, thanks for joining us!");
        javaMailSender.send(msg);

        return this.employeesRepository.save(newEmployee);
    }

    //GET
    public Page<Employee> findAll(int page, int size, String shortBy) {
        if (page > 50) page = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(shortBy));
        return this.employeesRepository.findAll(pageable);
    }

    //GET BY ID
    public Employee findById(UUID id) {
        return employeesRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //PUT
    public Employee update(UUID id, EmployeesDTO payload) {
        Employee found = this.findById(id);
        found.setSurname(payload.username());
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        return this.employeesRepository.save(found);
    }

    //DELETE
    public void delete(UUID id) {
        this.employeesRepository.delete(this.findById(id));
    }

    //IMG UPLOAD
    public void imgUpload(MultipartFile file, UUID id) throws IOException, MaxUploadSizeExceededException {
        Employee employee = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        employee.setAvatar(url);
        this.employeesRepository.save(employee);
    }

    //FIND BY EMAIL lo utilizzerò in authenticationService per verificare se l'email utilizzata per il login è esistente
    public Employee findByEmail(String email) {
        return this.employeesRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " not found on DB"));
    }
}
