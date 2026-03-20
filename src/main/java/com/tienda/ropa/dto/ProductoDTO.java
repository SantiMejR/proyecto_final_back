package com.tienda.ropa.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Long precio;

    @NotBlank(message = "La talla es obligatoria")
    private String talla;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private String marca;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;
}
