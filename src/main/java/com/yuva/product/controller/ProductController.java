package com.yuva.product.controller;

import com.yuva.product.dto.ProductDto;
import com.yuva.product.dto.ProductWithQuantity;
import com.yuva.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PostMapping("/quantity")
    public List<ProductWithQuantity> createProductsWithQuantity(@RequestBody ProductWithQuantity productWithQuantity) {
        return productService.createProductWithQuantity(productWithQuantity);
    }

    @PostMapping("/bulk/quantity")
    public List<ProductWithQuantity> createProductsBulkWithQuantity(@RequestBody List<ProductWithQuantity> productWithQuantityList) {
        return productService.createProductsBulkWithQuantity(productWithQuantityList);
    }
    @PostMapping("/bulk")
    public List<ProductDto> createProducts(@RequestBody List<ProductDto> productDtoList) {
        return productService.createProducts(productDtoList);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @GetMapping("")
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/filter")
    public List<ProductDto> getProductsByFilter(@RequestParam(required = false) String category, @RequestParam(required = false) Double min, @RequestParam(required = false) Double max) {
        return productService.findByFilter(category, min, max);
    }

    @GetMapping("/search")
    public List<ProductDto> getProductsByNameOrDescription(@RequestParam String query) {
        return productService.findByNameOrDescription(query, query);
    }


}
