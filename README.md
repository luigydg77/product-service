# Microservicio de Productos - Spring Boot, JPA, H2 y Spring Security

Este proyecto es un microservicio completamente configurado para la gestión de productos con persistencia en base de datos H2 en memoria y seguridad robusta con Spring Security.

## Características Incorporadas

1. **Spring Boot v3.3.0**: Estructura de microservicio moderna y ligera.
2. **Spring Data JPA**: Capa de persistencia completa utilizando Hibernate y H2.
3. **Soft Delete (Borrado Lógico)**: Los productos no se eliminan físicamente de la base de datos; en su lugar, se desactivan (`active = false`) mediante el método `DELETE` y se filtra en consultas.
4. **Paginación y Ordenación**: Consultas paginadas integradas nativamente en el endpoint de listado (`Pageable` de Spring Data).
5. **Spring Security**: Autenticación básica HTTP de dos roles preconfigurados (`USER` y `ADMIN`) de manera segura con encriptación `BCrypt`.
6. **Validación DTO**: Filtros robustos utilizando `jakarta.validation` en el cuerpo de las peticiones para asegurar datos limpios y consistentes.

---

## Estructura de Endpoints Protegidos

| Método | Endpoint | Rol Mínimo Requerido | Acción |
| :--- | :--- | :---: | :--- |
| **POST** | `/api/products` | **ROLE_ADMIN** | Crear nuevo producto |
| **GET** | `/api/products` | **ROLE_USER** o **ROLE_ADMIN** | Listar productos activos (con paginación) |
| **GET** | `/api/products/{id}` | **ROLE_USER** o **ROLE_ADMIN** | Obtener un producto por ID |
| **PUT** | `/api/products/{id}` | **ROLE_ADMIN** | Editar un producto existente |
| **DELETE** | `/api/products/{id}` | **ROLE_ADMIN** | Desactivar / Borrado Lógico de producto |
| **PATCH** | `/api/products/{id}/reactivate`| **ROLE_ADMIN** | Reactivar producto desactivado |

---

## Usuarios de Prueba (Configurados en `SecurityConfig.java`)

- **Administrador**:
  - Usuario: `admin`
  - Contraseña: `admin123`
- **Usuario Regular**:
  - Usuario: `user`
  - Contraseña: `user123`

---

## Cómo Ejecutar el Proyecto Localmente

### Requisitos Previos:
- Java JDK 17 o superior instalado.
- Maven 3.x instalado (u opcional usar el wrapper incluido).

### Pasos para levantar el servicio:

1. Descomprime el proyecto.
2. Abre una terminal dentro del directorio raíz.
3. Ejecuta el empaquetado y arranque del microservicio:
   ```bash
   mvn clean spring-boot:run
   ```
4. El servidor se iniciará en el puerto **8080** (`http://localhost:8080`).

---

## Acceso Interactivo Local en Desarrollo

- **Consola de Base de Datos H2**:
  - URL en Navegador: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:product_db`
  - Usuario: `sa`
  - Contraseña: `password123`
