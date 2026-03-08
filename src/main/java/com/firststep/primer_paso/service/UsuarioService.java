package com.firststep.primer_paso.service;

import com.firststep.primer_paso.dto.UsuarioRegistroDTO;
import com.firststep.primer_paso.entity.Usuario;
import com.firststep.primer_paso.repository.UsuarioRepository;
import com.firststep.primer_paso.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


}
