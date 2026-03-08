package com.firststep.primer_paso.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // desactivamos el csrf porque vamos a usar statless
                .sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // le decimos a spring que cada sesion trae su token no guarda sesiones
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuarios/registro", "/api/usuarios/login").permitAll() // le decimos que todas estas son publicas
                        .anyRequest().authenticated()) // y para cualquier otra debe pedir autenticacion
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();// BCrypt es el algoritmo para encriptar, no debemos guardar texto plano
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws  Exception{
        // Spring necesita este bean para manejar la autenticación
        // lo usaremos en el service para verificar usuario y contraseña
        return configuration.getAuthenticationManager();
    }
}
