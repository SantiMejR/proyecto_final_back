package com.tienda.ropa.mapper;

import com.tienda.ropa.dto.ProductoDTO;
import com.tienda.ropa.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .talla(producto.getTalla())
                .color(producto.getColor())
                .categoria(producto.getCategoria())
                .marca(producto.getMarca())
                .stock(producto.getStock())
                .build();
    }

    public Producto toEntity(ProductoDTO dto) {
        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .talla(dto.getTalla())
                .color(dto.getColor())
                .categoria(dto.getCategoria())
                .marca(dto.getMarca())
                .stock(dto.getStock())
                .build();
    }

    public void updateEntity(Producto producto, ProductoDTO dto) {
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setTalla(dto.getTalla());
        producto.setColor(dto.getColor());
        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setStock(dto.getStock());
    }
}
