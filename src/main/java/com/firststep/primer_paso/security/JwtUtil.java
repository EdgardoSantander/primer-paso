package com.firststep.primer_paso.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
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
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes); // convertimos el token a hmac256
         //return Keys.hmacShaKeyFor(secret.getBytes()); // esta no funciona
    }

    public String generateToken(String email, String rol){
        // aqui vamos a generar el token con el email y el rol
        return Jwts.builder()
                .setSubject(email) // el subject significa quien es el usuario
                .claim("rol",rol) // agregamos el rol como extra
                .claim("type","ACCESS")
                .setIssuedAt(new Date(System.currentTimeMillis())) // pasamos la fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)) // expira en 15min
                .signWith(getSigningKey()) // aqui pasamos nuestra clave para registrar
                .compact(); // aqui construimos y esto regresaria el token como string
    }

    public String generateRefreshToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .claim("type","REFRESH")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
