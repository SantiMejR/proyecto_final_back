package com.tienda.ropa.repository;

import com.tienda.ropa.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByVendedorId(Long vendedorId);

    List<Venta> findByClienteId(Long clienteId);

    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
