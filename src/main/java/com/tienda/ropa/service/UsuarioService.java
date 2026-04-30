package com.tienda.ropa.service;

import com.tienda.ropa.dto.UsuarioCreateDTO;
import com.tienda.ropa.dto.UsuarioDTO;
import com.tienda.ropa.exception.ResourceNotFoundException;
import com.tienda.ropa.mapper.UsuarioMapper;
import com.tienda.ropa.model.Rol;
import com.tienda.ropa.model.Usuario;
import com.tienda.ropa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO crear(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(guardado);
    }

    public UsuarioDTO actualizar(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        // Verificar si el email está siendo cambiado y si ya existe
        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        usuarioMapper.updateEntity(usuario, dto);
        Usuario actualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Autentica al usuario por email + password (texto plano, demo academica).
     * Lanza IllegalArgumentException si las credenciales no coinciden.
     */
    @Transactional(readOnly = true)
    public UsuarioDTO autenticar(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas"));

        if (!usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }
        return usuarioMapper.toDTO(usuario);
    }
}
