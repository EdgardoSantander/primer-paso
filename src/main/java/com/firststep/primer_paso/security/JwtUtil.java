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

    // 15 minutos en milisegundos
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;

    // 7 dias en milisegundos
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 365;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes()); // convertimos el token a hmac256
    }

    public String generateToken(String email, String rol){
        // aqui vamos a generar el token con el email y el rol
        return Jwts.builder()
                .setSubject(email) // el subject significa quien es el usuario
                .claim("rol",rol) // agregamos el rol como extra
                .setIssuedAt(new Date()) // pasamos la fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)) // expira en 24 horas
                .signWith(getSigningKey()) // aqui pasamos nuestra clave para registrar
                .compact(); // aqui construimos y esto regresaria el token como string
    }

    public String generateRefreshToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJwt(token);
                    return true;
        }catch (Exception e){
            return false;
        }

    }
}
