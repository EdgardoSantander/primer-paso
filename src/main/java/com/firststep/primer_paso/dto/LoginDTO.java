package com.firststep.primer_paso.dto;

import com.firststep.primer_paso.enums.Rol;
import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
