package com.yuva.product.controller;

import com.yuva.product.dto.ProductDto;
import com.yuva.product.dto.ProductWithQuantity;
import com.yuva.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("It seems, You don't have access to go ahead");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @PostMapping("/quantity")
    public ResponseEntity<?> createProductsWithQuantity(@RequestBody ProductWithQuantity productWithQuantity, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("It seems, You don't have access to go ahead");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProductWithQuantity(productWithQuantity,request));
    }

    @PostMapping("/bulk/quantity")
    public ResponseEntity<?> createProductsBulkWithQuantity(@RequestBody List<ProductWithQuantity> productWithQuantityList, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("It seems, You don't have access to go ahead");
    }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProductsBulkWithQuantity(productWithQuantityList,request));
    }
    @PostMapping("/bulk")
    public ResponseEntity<?> createProducts(@RequestBody List<ProductDto> productDtoList, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("It seems, You don't have access to go ahead");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProducts(productDtoList));
    }

    @GetMapping("get/id")
    public ProductDto getProduct(@RequestParam String id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("It seems, You don't have access to go ahead");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("It seems, You don't have access to go ahead");
        }
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully");
    }

    @GetMapping("/get/all")
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("get/filter")
    public List<ProductDto> getProductsByFilter(@RequestParam(required = false) String category, @RequestParam(required = false) Double min, @RequestParam(required = false) Double max) {
        return productService.findByFilter(category, min, max);
    }

    @GetMapping("get/search")
    public List<ProductDto> getProductsByNameOrDescription(@RequestParam String query) {
        return productService.findByNameOrDescription(query, query);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllProducts(HttpServletRequest request) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
            throw new RuntimeException("Only admin can delete product");
        }
        productService.deleteAllProducts();
    }


}
