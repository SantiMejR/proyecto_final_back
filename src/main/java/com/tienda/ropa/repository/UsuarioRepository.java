package com.tienda.ropa.repository;

import com.tienda.ropa.model.Rol;
import com.tienda.ropa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRol(Rol rol);

    boolean existsByEmail(String email);
}
