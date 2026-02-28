package com.firststep.primer_paso.dto;

import com.firststep.primer_paso.enums.Rol;
import lombok.Data;

@Data
public class UsuarioRegistroDTO {
    private String nombre;
    private String email;
    private String password;
    private Rol rol; // ejemplo: CONDUCTOR - PASAJERO
}
