# Proyecto Final — Backend

API REST para una tienda de ropa: gestiona usuarios, productos y ventas.
Construida con **Spring Boot 3.2** y **Java 21**, base de datos **H2** en memoria.

Es uno de **tres repositorios** que conforman el proyecto:

| Repositorio | Tecnología | Puerto | Rol |
|---|---|---|---|
| `proyecto_final_front` | HTML + JS + Bootstrap | (estático) | UI |
| `proyecto_final_back` | Spring Boot + Java 21 | `8080` | **Este repo** — CRUD |
| `proyecto_final_analititca` | Flask + pandas | `5000` | Simulación y análisis |

---

## Tecnologías

- **Java 21**
- **Spring Boot 3.2.3**
  - **Spring Web** — controladores REST
  - **Spring Data JPA** — persistencia
  - **Spring Validation** — validación con anotaciones (`@Valid`, `@NotBlank`, etc.)
- **H2 Database** — motor relacional en memoria (no requiere instalar nada)
- **Lombok** — reduce boilerplate de getters, setters y constructores
- **Maven** — gestor de dependencias y build
- **CORS** habilitado a nivel de controller con `@CrossOrigin(origins = "*")`

---

## Arquitectura

Monolítica por capas dentro del paquete `com.tienda.ropa`:

```
com.tienda.ropa
├── model/          → Entidades JPA (Usuario, Producto, Venta, DetalleVenta)
├── dto/            → Objetos de transferencia (Create / Read DTOs)
├── mapper/         → Conversión entidad ↔ DTO
├── repository/     → Spring Data JPA (extensión de JpaRepository)
├── service/        → Lógica de negocio
├── controller/     → REST controllers (/api/*)
├── exception/      → Manejo global de excepciones
└── DataLoader.java → Precarga datos de ejemplo al iniciar
```

### Entidades

| Entidad | Descripción |
|---|---|
| **Usuario** | Usuarios del sistema con roles `ADMINISTRADOR`, `VENDEDOR`, `CLIENTE` |
| **Producto** | Prendas de ropa (nombre, precio COP, talla, color, categoría, marca, stock) |
| **Venta** | Registro de venta con vendedor, cliente y lista de productos |
| **DetalleVenta** | Línea de detalle: producto, cantidad, precio unitario, subtotal |

---

## Cómo funciona

### Flujo de una petición

```
Cliente HTTP ──► Controller (/api/...)
                     │
                     ▼
                 Service (lógica de negocio)
                     │
                     ▼
                 Repository (Spring Data JPA)
                     │
                     ▼
                 H2 Database (en memoria)
```

- Los DTOs separan lo que viaja por la red de las entidades JPA.
- Los mappers (uno por agregado) hacen la conversión.
- `GlobalExceptionHandler` traduce excepciones a respuestas HTTP coherentes
  (404 para `ResourceNotFoundException`, 400 para validación, etc.).
- CORS está abierto (`*`) en cada controller para que el front local pueda consumirlo.

### Datos precargados

Al arrancar, `DataLoader` inserta:

**Usuarios**
| ID | Nombre | Email | Rol |
|---|---|---|---|
| 1 | Carlos García | admin@tienda.com | ADMINISTRADOR |
| 2 | María López | vendedor@tienda.com | VENDEDOR |
| 3 | Juan Pérez | cliente@tienda.com | CLIENTE |

**Productos**
| ID | Nombre | Precio (COP) | Categoría | Stock |
|---|---|---|---|---|
| 1 | Camiseta Básica Algodón | $45.000 | Camisetas | 50 |
| 2 | Jean Slim Fit | $120.000 | Pantalones | 30 |
| 3 | Vestido Floral Verano | $89.000 | Vestidos | 20 |
| 4 | Chaqueta Denim Clásica | $150.000 | Chaquetas | 15 |
| 5 | Falda Plisada Midi | $75.000 | Faldas | 25 |

---

## Cómo usarlo

### Prerrequisitos

- **Java 21**
- **Maven 3.9+** (o usar el wrapper `./mvnw`)

### Ejecutar

```bash
mvn spring-boot:run
# o
./mvnw spring-boot:run
```

La API queda en [http://localhost:8080](http://localhost:8080).

### Consola H2 (inspeccionar la BD)

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **JDBC URL:** `jdbc:h2:mem:tiendaropa`
- **User:** `sa`
- **Password:** *(vacío)*

---

## Endpoints

### Usuarios — `/api/usuarios`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/usuarios` | Listar todos |
| `GET` | `/api/usuarios/{id}` | Obtener por ID |
| `GET` | `/api/usuarios/rol/{rol}` | Listar por rol |
| `POST` | `/api/usuarios` | Crear |
| `PUT` | `/api/usuarios/{id}` | Actualizar |
| `DELETE` | `/api/usuarios/{id}` | Eliminar |

```json
POST /api/usuarios
{
  "nombre": "Ana",
  "apellido": "Martínez",
  "email": "ana@tienda.com",
  "password": "ana12345",
  "telefono": "3101234567",
  "rol": "CLIENTE"
}
```

### Productos — `/api/productos`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/productos` | Listar todos |
| `GET` | `/api/productos/{id}` | Obtener por ID |
| `GET` | `/api/productos/categoria/{categoria}` | Listar por categoría |
| `GET` | `/api/productos/buscar?nombre=camiseta` | Buscar por nombre |
| `POST` | `/api/productos` | Crear |
| `PUT` | `/api/productos/{id}` | Actualizar |
| `DELETE` | `/api/productos/{id}` | Eliminar |

```json
POST /api/productos
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

### Ventas — `/api/ventas`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/ventas` | Listar todas |
| `GET` | `/api/ventas/{id}` | Obtener por ID |
| `GET` | `/api/ventas/vendedor/{vendedorId}` | Ventas por vendedor |
| `GET` | `/api/ventas/cliente/{clienteId}` | Ventas por cliente |
| `POST` | `/api/ventas` | Crear (descuenta stock) |
| `DELETE` | `/api/ventas/{id}` | Eliminar (restaura stock) |

```json
POST /api/ventas
{
  "vendedorId": 2,
  "clienteId": 3,
  "detalles": [
    { "productoId": 1, "cantidad": 2 },
    { "productoId": 2, "cantidad": 1 }
  ]
}
```

---

## Conexión con los demás repos

- El **frontend** consume estos endpoints desde `services/api.js`.
- La **analítica** trabaja con datos simulados; **no** consume este backend.

---

## Flujo de ramas

- Trabajo en **`develop`**.
- Merge a **`main`** cuando develop esté estable.
