package com.yuva.product.dto;

public record ProductWithQuantity(
        String id,
        String name,
        String description,
        String category,
        Double price,
        Integer quantity
) {
}
