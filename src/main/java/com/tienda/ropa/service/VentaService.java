package com.tienda.ropa.service;

import com.tienda.ropa.dto.DetalleVentaCreateDTO;
import com.tienda.ropa.dto.VentaCreateDTO;
import com.tienda.ropa.dto.VentaDTO;
import com.tienda.ropa.exception.ResourceNotFoundException;
import com.tienda.ropa.mapper.VentaMapper;
import com.tienda.ropa.model.*;
import com.tienda.ropa.repository.ProductoRepository;
import com.tienda.ropa.repository.UsuarioRepository;
import com.tienda.ropa.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper ventaMapper;

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerTodas() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VentaDTO obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
        return ventaMapper.toDTO(venta);
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorVendedor(Long vendedorId) {
        return ventaRepository.findByVendedorId(vendedorId).stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorCliente(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId).stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VentaDTO crear(VentaCreateDTO dto) {
        // Validar vendedor
        Usuario vendedor = usuarioRepository.findById(dto.getVendedorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vendedor no encontrado con id: " + dto.getVendedorId()));

        if (vendedor.getRol() != Rol.VENDEDOR && vendedor.getRol() != Rol.ADMINISTRADOR) {
            throw new IllegalArgumentException(
                    "El usuario con id " + dto.getVendedorId() + " no tiene rol de VENDEDOR o ADMINISTRADOR");
        }

        // Validar cliente
        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con id: " + dto.getClienteId()));

        // Crear la venta
        Venta venta = Venta.builder()
                .fecha(LocalDateTime.now())
                .vendedor(vendedor)
                .cliente(cliente)
                .detalles(new ArrayList<>())
                .total(0L)
                .build();

        Long total = 0L;

        for (DetalleVentaCreateDTO detalleDTO : dto.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Producto no encontrado con id: " + detalleDTO.getProductoId()));

            // Validar stock disponible
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: " + producto.getNombre()
                                + ". Disponible: " + producto.getStock()
                                + ", Solicitado: " + detalleDTO.getCantidad());
            }

            Long subtotal = producto.getPrecio() * detalleDTO.getCantidad();

            DetalleVenta detalle = DetalleVenta.builder()
                    .venta(venta)
                    .producto(producto)
                    .cantidad(detalleDTO.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .subtotal(subtotal)
                    .build();

            venta.getDetalles().add(detalle);
            total += subtotal;

            // Descontar stock
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        venta.setTotal(total);
        Venta guardada = ventaRepository.save(venta);
        return ventaMapper.toDTO(guardada);
    }

    public void eliminar(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));

        // Restaurar stock de los productos
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        ventaRepository.deleteById(id);
    }
}
