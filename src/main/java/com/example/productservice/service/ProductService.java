package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    
    // Crear un producto
    ProductResponse createProduct(ProductRequest request);
    
    // Obtener un producto activo por ID
    ProductResponse getProductById(Long id);
    
    // Listar todos los productos activos con paginación
    Page<ProductResponse> getAllActiveProducts(Pageable pageable);
    
    // Listar absolutamente todos (activos e inactivos) con paginación
    Page<ProductResponse> getAllProducts(Pageable pageable);
    
    // Actualizar producto existente
    ProductResponse updateProduct(Long id, ProductRequest request);
    
    // Desactivar un producto (borrado lógico / soft delete)
    void deactivateProduct(Long id);
    
    // Activar/Reactivar un producto
    void reactivateProduct(Long id);
}
