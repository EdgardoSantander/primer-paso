package com.firststep.primer_paso.service;

import com.firststep.primer_paso.dto.LoginDTO;
import com.firststep.primer_paso.dto.UsuarioRegistroDTO;
import com.firststep.primer_paso.entity.Usuario;
import com.firststep.primer_paso.exception.GlobalExceptions;
import com.firststep.primer_paso.repository.RefreshTokenRepository;
import com.firststep.primer_paso.repository.UsuarioRepository;
import com.firststep.primer_paso.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public Map<String,String> registrar(UsuarioRegistroDTO usuarioRegistroDTO){

        if (usuarioRepository.findByEmail(usuarioRegistroDTO.getEmail()).isPresent()){
            throw  new GlobalExceptions.emailYaExistenteException("Ya existe un registro con esa direccion de correo electronico");
        }

        Usuario usuario = Usuario.builder()
                .nombre(usuarioRegistroDTO.getNombre())
                .email(usuarioRegistroDTO.getEmail())
                .password(passwordEncoder.encode(usuarioRegistroDTO.getPassword()))
                .rol(usuarioRegistroDTO.getRol())
                .build();

        usuarioRepository.save(usuario); // guardamos el nuevo usuario

        String accesToken = jwtUtil.generateToken(usuario.getEmail(),usuario.getRol().name()); // usamos name o tambien se puede usar toString
        String refreshToken = refreshTokenService.createRefreshToken(usuario).getToken();

        return Map.of(
            "accessToken", accesToken,
            "refreshToken",refreshToken
        );
    }

    public Map<String,String> login(LoginDTO loginDTO){

        //verificamos las credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );

        // recuperamos el usuario o regresamos una exception se que no lo encontramos
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new GlobalExceptions.usuarioNoEncontradoException("Credenciales Incorrectas"));

        var accessToken = jwtUtil.generateToken(usuario.getEmail(),usuario.getRol().name()); // regresamos el token
        var refreshToken = refreshTokenService.createRefreshToken(usuario).getToken();

        return Map.of(
                "accessToken",accessToken,
                "refreshToken",refreshToken
        );

    }
}
