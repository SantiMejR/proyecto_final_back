package com.tienda.ropa;

import com.tienda.ropa.model.*;
import com.tienda.ropa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {

        // ==================== USUARIOS ====================

        Usuario admin = usuarioRepository.save(Usuario.builder()
                .nombre("Carlos")
                .apellido("García")
                .email("admin@tienda.com")
                .password("admin123")
                .telefono("3001234567")
                .rol(Rol.ADMINISTRADOR)
                .build());

        Usuario vendedor = usuarioRepository.save(Usuario.builder()
                .nombre("María")
                .apellido("López")
                .email("vendedor@tienda.com")
                .password("vendedor123")
                .telefono("3009876543")
                .rol(Rol.VENDEDOR)
                .build());

        Usuario cliente = usuarioRepository.save(Usuario.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .email("cliente@tienda.com")
                .password("cliente123")
                .telefono("3005551234")
                .rol(Rol.CLIENTE)
                .build());

        // ==================== PRODUCTOS (PRENDAS) ====================

        productoRepository.save(Producto.builder()
                .nombre("Camiseta Básica Algodón")
                .descripcion("Camiseta de algodón 100%, corte regular")
                .precio(45000L)
                .talla("M")
                .color("Blanco")
                .categoria("Camisetas")
                .marca("Nike")
                .stock(50)
                .build());

        productoRepository.save(Producto.builder()
                .nombre("Jean Slim Fit")
                .descripcion("Jean stretch slim fit, tiro medio")
                .precio(120000L)
                .talla("32")
                .color("Azul Oscuro")
                .categoria("Pantalones")
                .marca("Levi's")
                .stock(30)
                .build());

        productoRepository.save(Producto.builder()
                .nombre("Vestido Floral Verano")
                .descripcion("Vestido largo con estampado floral, tela liviana")
                .precio(89000L)
                .talla("S")
                .color("Rosa")
                .categoria("Vestidos")
                .marca("Zara")
                .stock(20)
                .build());

        productoRepository.save(Producto.builder()
                .nombre("Chaqueta Denim Clásica")
                .descripcion("Chaqueta de jean clásica con bolsillos frontales")
                .precio(150000L)
                .talla("L")
                .color("Azul Claro")
                .categoria("Chaquetas")
                .marca("Adidas")
                .stock(15)
                .build());

        productoRepository.save(Producto.builder()
                .nombre("Falda Plisada Midi")
                .descripcion("Falda plisada midi con cintura elástica")
                .precio(75000L)
                .talla("M")
                .color("Negro")
                .categoria("Faldas")
                .marca("H&M")
                .stock(25)
                .build());

        System.out.println("========================================");
        System.out.println("  Datos de ejemplo cargados exitosamente");
        System.out.println("  Usuarios: " + usuarioRepository.count());
        System.out.println("  Productos: " + productoRepository.count());
        System.out.println("========================================");
    }
}
