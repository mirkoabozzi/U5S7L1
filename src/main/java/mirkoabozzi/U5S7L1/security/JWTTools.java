package mirkoabozzi.U5S7L1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mirkoabozzi.U5S7L1.entities.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) //data rilascio
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //data scadenza
                .subject(String.valueOf(employee.getId())) // id del proprietario del token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) //firma il token utilizzando la key segreta present ein env.properties importata nella classe con il @Value
                .compact(); // genera il token
    }
}
