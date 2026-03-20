package com.tienda.ropa.controller;

import com.tienda.ropa.dto.VentaCreateDTO;
import com.tienda.ropa.dto.VentaDTO;
import com.tienda.ropa.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> obtenerTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<VentaDTO>> obtenerPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(ventaService.obtenerPorVendedor(vendedorId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.obtenerPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crear(@Valid @RequestBody VentaCreateDTO dto) {
        return new ResponseEntity<>(ventaService.crear(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
