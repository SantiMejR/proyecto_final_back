package com.tienda.ropa.controller;

import com.tienda.ropa.dto.LoginDTO;
import com.tienda.ropa.dto.UsuarioDTO;
import com.tienda.ropa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;

    /**
     * Valida las credenciales contra los usuarios persistidos en H2.
     * Devuelve el UsuarioDTO si coinciden, o 400 con mensaje generico si no.
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginDTO dto) {
        UsuarioDTO usuario = usuarioService.autenticar(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(usuario);
    }
}
