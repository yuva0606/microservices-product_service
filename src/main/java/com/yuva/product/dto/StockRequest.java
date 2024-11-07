package com.yuva.product.dto;

public record StockRequest(
    String productId,
    int quantity
) {
}
