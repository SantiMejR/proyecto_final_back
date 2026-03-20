package com.tienda.ropa.mapper;

import com.tienda.ropa.dto.DetalleVentaDTO;
import com.tienda.ropa.dto.VentaDTO;
import com.tienda.ropa.model.DetalleVenta;
import com.tienda.ropa.model.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public VentaDTO toDTO(Venta venta) {
        List<DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
                .map(this::toDetalleDTO)
                .collect(Collectors.toList());

        return VentaDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .vendedorId(venta.getVendedor().getId())
                .vendedorNombre(venta.getVendedor().getNombre() + " " + venta.getVendedor().getApellido())
                .clienteId(venta.getCliente().getId())
                .clienteNombre(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido())
                .detalles(detallesDTO)
                .total(venta.getTotal())
                .build();
    }

    public DetalleVentaDTO toDetalleDTO(DetalleVenta detalle) {
        return DetalleVentaDTO.builder()
                .id(detalle.getId())
                .productoId(detalle.getProducto().getId())
                .productoNombre(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
