package com.tienda.ropa.mapper;

import com.tienda.ropa.dto.UsuarioCreateDTO;
import com.tienda.ropa.dto.UsuarioDTO;
import com.tienda.ropa.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol())
                .build();
    }

    public Usuario toEntity(UsuarioCreateDTO dto) {
        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .rol(dto.getRol())
                .build();
    }

    public void updateEntity(Usuario usuario, UsuarioCreateDTO dto) {
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(dto.getRol());
    }
}
