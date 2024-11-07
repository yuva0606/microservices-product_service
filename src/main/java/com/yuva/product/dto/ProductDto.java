package com.yuva.product.dto;


public record ProductDto(
    String id,
    String name,
    String description,
    String category,
    Double price
) {
}
