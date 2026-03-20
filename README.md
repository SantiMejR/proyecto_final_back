# 👕 Tienda de Ropa - API REST

API REST para la gestión de una tienda de ropa construida con **Spring Boot 3.2**, **Java 21** y **H2 Database**.

## 🏗️ Arquitectura

Arquitectura monolítica por capas:

```
com.tienda.ropa
├── model/          → Entidades JPA (Usuario, Producto, Venta, DetalleVenta)
├── dto/            → Objetos de transferencia de datos
├── mapper/         → Conversión entre entidades y DTOs
├── repository/     → Repositorios Spring Data JPA
├── service/        → Lógica de negocio
├── controller/     → Controladores REST
└── exception/      → Manejo global de excepciones
```

## 📦 Entidades

| Entidad | Descripción |
|---------|-------------|
| **Usuario** | Usuarios del sistema con roles: `ADMINISTRADOR`, `VENDEDOR`, `CLIENTE` |
| **Producto** | Prendas de ropa (nombre, precio COP, talla, color, categoría, marca, stock) |
| **Venta** | Registro de ventas con vendedor, cliente y lista de productos |
| **DetalleVenta** | Línea de detalle: producto, cantidad, precio unitario, subtotal |

## 🚀 Cómo ejecutar

### Prerrequisitos
- Java 21
- Maven 3.9+

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

### Consola H2
Accede a la base de datos en memoria: `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:tiendaropa`
- **User:** `sa`
- **Password:** *(vacío)*

## 📋 Endpoints

### Usuarios (`/api/usuarios`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/usuarios` | Listar todos los usuarios |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID |
| GET | `/api/usuarios/rol/{rol}` | Listar usuarios por rol |
| POST | `/api/usuarios` | Crear usuario |
| PUT | `/api/usuarios/{id}` | Actualizar usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario |

**Ejemplo POST `/api/usuarios`:**
```json
{
  "nombre": "Ana",
  "apellido": "Martínez",
  "email": "ana@tienda.com",
  "password": "ana12345",
  "telefono": "3101234567",
  "rol": "CLIENTE"
}
```

### Productos (`/api/productos`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/categoria/{categoria}` | Listar por categoría |
| GET | `/api/productos/buscar?nombre=camiseta` | Buscar por nombre |
| POST | `/api/productos` | Crear producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |

**Ejemplo POST `/api/productos`:**
```json
{
  "nombre": "Polo Deportivo",
  "descripcion": "Polo de poliéster con tecnología dry-fit",
  "precio": 85000,
  "talla": "L",
  "color": "Azul",
  "categoria": "Camisetas",
  "marca": "Under Armour",
  "stock": 40
}
```

### Ventas (`/api/ventas`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/ventas` | Listar todas las ventas |
| GET | `/api/ventas/{id}` | Obtener venta por ID |
| GET | `/api/ventas/vendedor/{vendedorId}` | Ventas por vendedor |
| GET | `/api/ventas/cliente/{clienteId}` | Ventas por cliente |
| POST | `/api/ventas` | Crear venta |
| DELETE | `/api/ventas/{id}` | Eliminar venta (restaura stock) |

**Ejemplo POST `/api/ventas`:**
```json
{
  "vendedorId": 2,
  "clienteId": 3,
  "detalles": [
    { "productoId": 1, "cantidad": 2 },
    { "productoId": 2, "cantidad": 1 }
  ]
}
```

## 🗂️ Datos precargados

**Usuarios:**
| ID | Nombre | Email | Rol |
|----|--------|-------|-----|
| 1 | Carlos García | admin@tienda.com | ADMINISTRADOR |
| 2 | María López | vendedor@tienda.com | VENDEDOR |
| 3 | Juan Pérez | cliente@tienda.com | CLIENTE |

**Productos:**
| ID | Nombre | Precio (COP) | Categoría | Stock |
|----|--------|---------------|-----------|-------|
| 1 | Camiseta Básica Algodón | $45.000 | Camisetas | 50 |
| 2 | Jean Slim Fit | $120.000 | Pantalones | 30 |
| 3 | Vestido Floral Verano | $89.000 | Vestidos | 20 |
| 4 | Chaqueta Denim Clásica | $150.000 | Chaquetas | 15 |
| 5 | Falda Plisada Midi | $75.000 | Faldas | 25 |

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Lombok**
- **Jakarta Validation**