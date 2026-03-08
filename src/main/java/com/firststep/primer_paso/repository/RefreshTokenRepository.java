package com.firststep.primer_paso.repository;

import com.firststep.primer_paso.entity.RefreshToken;
import com.firststep.primer_paso.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    // busca el refresh token en la BD para validarlo

    Optional<RefreshToken> findByUsuario(Usuario usuario);
    // busca si el usuario ya tiene un refresh token activo

    void deleteByUsuario(Usuario usuario);
    // elimina el refresh token cuando el usuario cierra sesion
}
