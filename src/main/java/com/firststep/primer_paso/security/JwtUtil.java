package com.firststep.primer_paso.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes()); // convertimos el token a hmac256
    }

    private String generateToken(String email, String rol){
        // aqui vamos a generar el token con el email y el rol
        return Jwts.builder()
                .setSubject(email) // el subject significa quien es el usuario
                .claim("rol",rol) // agregamos el rol como extra
                .setIssuedAt(new Date()) // pasamos la fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // expira en 24 horas
                .signWith(getSigningKey()) // aqui pasamos nuestra clave para registrar
                .compact(); // aqui construimos y esto regresaria el token como string
    }
}
