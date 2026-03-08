package com.firststep.primer_paso.service;

import com.firststep.primer_paso.entity.RefreshToken;
import com.firststep.primer_paso.entity.Usuario;
import com.firststep.primer_paso.exception.GlobalExceptions;
import com.firststep.primer_paso.repository.RefreshTokenRepository;
import com.firststep.primer_paso.repository.UsuarioRepository;
import com.firststep.primer_paso.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public RefreshToken createRefreshToken(Usuario usuario){

        // ambas formas son correctas pero la segunda es mas moderna
        //refreshTokenRepository.findByUsuario(usuario).ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
        refreshTokenRepository.findByUsuario(usuario).ifPresent(refreshTokenRepository::delete);

        // crearemos el nuevo refreshToken
        RefreshToken refreshToken = RefreshToken.builder()
                .token(jwtUtil.generateRefreshToken(usuario.getEmail()))
                .usuario(usuario)
                .dateExpires(LocalDateTime.now().plusDays(7))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public String renovarAccessToken(String token){
        // primero buscamos el token a ver si no fue alterado o trae algo raro
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow( () -> new GlobalExceptions.tokenInvalidoException("Token invalido"));

        // despues vemos si no esta expirado
        if (refreshToken.getDateExpires().isBefore(LocalDateTime.now())){ // revisamos si nuestra fecha ya paso a la fecha de hoy
            refreshTokenRepository.delete(refreshToken);
            throw new GlobalExceptions.tokenExpiradoException("El token ya esta expirado, inicie sesion nuevamente");
        }

        Usuario usuario = refreshToken.getUsuario();
        return jwtUtil.generateToken(usuario.getEmail(),usuario.getRol().name());
    }

    public void eliminarRefreshToken(Usuario usuario) {
        // cuando el usuario cierra sesion eliminamos su refresh token
        refreshTokenRepository.deleteByUsuario(usuario);
    }
}
