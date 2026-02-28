package com.firststep.primer_paso.repository;

import com.firststep.primer_paso.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    // encontramos un usuario por email, optional puede o no regresar un usuario, en este caso seria puede o no existir un usuario
    Optional<Usuario> findByEmail(String email);
    // find by email seria como un Select * from Usuarios Where email = :email;
}
