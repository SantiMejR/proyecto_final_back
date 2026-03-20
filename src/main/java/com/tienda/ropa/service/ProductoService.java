package com.tienda.ropa.service;

import com.tienda.ropa.dto.ProductoDTO;
import com.tienda.ropa.exception.ResourceNotFoundException;
import com.tienda.ropa.mapper.ProductoMapper;
import com.tienda.ropa.model.Producto;
import com.tienda.ropa.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return productoMapper.toDTO(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = productoMapper.toEntity(dto);
        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDTO(guardado);
    }

    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        productoMapper.updateEntity(producto, dto);
        Producto actualizado = productoRepository.save(producto);
        return productoMapper.toDTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }
}
