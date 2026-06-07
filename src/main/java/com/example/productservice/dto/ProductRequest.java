package com.example.productservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0.0")
    private BigDecimal price;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "El stock mínimo permitido es 0")
    private Integer stock;
}
