package com.tienda.ropa.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {

    private Long id;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private Long precioUnitario;
    private Long subtotal;
}
