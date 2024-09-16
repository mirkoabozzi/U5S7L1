package mirkoabozzi.U5S7L1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mirkoabozzi.U5S7L1.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization"); //cerco nell header l'autorizzazione
        if (header == null || !header.startsWith("Bearer ")) // verifico la presenza della stringa Bearer prima del token quindi se non ! inizia con Bearer o se è null
            throw new UnauthorizedException("Token required"); // lancio l'eccezione se non passo le due condizioni
        String token = header.substring(7); // rimuovo i primi 7 caratteri contenenti Bearer + spazio vuoto
        jwtTools.verifyToken(token); //verifico il token col metodo creato in JWTTools
        filterChain.doFilter(request, response); // applico i filtri
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/authentication/**", request.getServletPath()); // escludo dal filtro i path che contengono /authentication all'interno
    }
}
