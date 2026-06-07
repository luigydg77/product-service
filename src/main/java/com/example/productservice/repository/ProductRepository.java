package com.example.productservice.repository;

import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Listar solo productos activos que no han sido borrados (Soft Delete), usando paginación
    Page<Product> findByActiveTrue(Pageable pageable);
    
    // Listar todos pero filtrado por estado de actividad
    Page<Product> findByActive(boolean active, Pageable pageable);

    // Buscar un producto específico de ID solo si está activo
    Optional<Product> findByIdAndActiveTrue(Long id);
}
