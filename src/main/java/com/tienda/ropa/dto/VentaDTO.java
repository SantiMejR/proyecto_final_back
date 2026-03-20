package com.tienda.ropa.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {

    private Long id;
    private LocalDateTime fecha;
    private Long vendedorId;
    private String vendedorNombre;
    private Long clienteId;
    private String clienteNombre;
    private List<DetalleVentaDTO> detalles;
    private Long total;
}
