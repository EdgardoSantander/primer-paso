package com.firststep.primer_paso.controller;

import com.firststep.primer_paso.dto.LoginDTO;
import com.firststep.primer_paso.dto.UsuarioDTO;
import com.firststep.primer_paso.dto.UsuarioRegistroDTO;
import com.firststep.primer_paso.entity.Usuario;
import com.firststep.primer_paso.service.RefreshTokenService;
import com.firststep.primer_paso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("taxi/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registro(@RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,String>> refresh(@RequestBody Map<String,String> request){
       String nuevoAccessToken = refreshTokenService.renovarAccessToken(request.get("refreshToken"));
       return ResponseEntity.ok(Map.of("accessToken",nuevoAccessToken));
    }

    @GetMapping("/listar/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

}






