package com.firststep.primer_paso.dto;

import com.firststep.primer_paso.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol; // ejemplo: CONDUCTOR - PASAJERO
}
