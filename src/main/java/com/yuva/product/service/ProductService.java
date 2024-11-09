package com.yuva.product.service;

import com.yuva.product.dto.ProductDto;
import com.yuva.product.dto.ProductWithQuantity;
import com.yuva.product.dto.StockRequest;
import com.yuva.product.feignclient.InventoryClient;
import com.yuva.product.model.Product;
import com.yuva.product.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final InventoryClient inventoryClient;

    public ProductDto createProduct(ProductDto productDto) {
        Product product = toProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return toProductDto(savedProduct);
    }

    public List<ProductDto> createProducts(List<ProductDto> productDtos){
        List<Product> products = productDtos.stream().map(ProductService::toProduct).toList();
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products){
            productDtoList.add(toProductDto(productRepository.save(product)));
        }
        return productDtoList;
    }

    public ProductDto getProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return toProductDto(product);
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(ProductService::toProductDto).toList();
    }

    public ProductDto updateProduct(String id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setCategory(productDto.category());
        product.setPrice(productDto.price());
        Product updatedProduct = productRepository.save(product);
        return toProductDto(updatedProduct);
    }

    public void deleteProduct(String id) {
        if(productRepository.existsById(id)){
        productRepository.deleteById(id);
        }else{
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public List<ProductDto> findByNameOrDescription(String name, String description) {
        return productRepository.findByNameContainingOrDescriptionContaining(name, description)
                .stream().map(ProductService::toProductDto).toList();
    }

    public List<ProductDto> findByFilter(String category, Double min, Double max) {
        if(category != null && min != null && max != null){
            return productRepository.findByCategoryAndPriceBetween(category, min, max)
                    .stream().map(ProductService::toProductDto).toList();
        }else if(category != null && min != null){
            return productRepository.findByCategoryAndPriceGreaterThanEqual(category, min)
                    .stream().map(ProductService::toProductDto).toList();
        }else if(category != null && max != null){
            return productRepository.findByCategoryAndPriceLessThanEqual(category, max)
                    .stream().map(ProductService::toProductDto).toList();
        }else if(min != null && max != null){
            return productRepository.findByPriceBetween(min, max)
                    .stream().map(ProductService::toProductDto).toList();
        }else if(category != null){
            return productRepository.findByCategory(category)
                    .stream().map(ProductService::toProductDto).toList();
        }
        else{
            return productRepository.findAll()
                    .stream().map(ProductService::toProductDto).toList();
        }
    }



    private static Product toProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.id());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setCategory(productDto.category());
        product.setPrice(productDto.price());
        return product;
    }

    private static ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(),product.getName(),product.getDescription(),product.getCategory(),product.getPrice());
    }

    private static Product toProduct(ProductWithQuantity productWithQuantity) {
        Product product = new Product();
        product.setId(productWithQuantity.id());
        product.setName(productWithQuantity.name());
        product.setDescription(productWithQuantity.description());
        product.setCategory(productWithQuantity.category());
        product.setPrice(productWithQuantity.price());
        return product;
    }

    private static ProductWithQuantity toProductWithQuantity(Product product, int quantity) {
        return new ProductWithQuantity(product.getId(),product.getName(),
                product.getDescription(),product.getCategory(),product.getPrice(),quantity);
    }

    public List<ProductWithQuantity> createProductWithQuantity(ProductWithQuantity productWithQuantity, HttpServletRequest request) {
        Product product = productRepository.save(toProduct(productWithQuantity));
        Integer quantity = (Integer) inventoryClient.addStock(new StockRequest(product.getId(), productWithQuantity.quantity()), request).getBody();
        return List.of(toProductWithQuantity(product, quantity));
    }

    public List<ProductWithQuantity> createProductsBulkWithQuantity(List<ProductWithQuantity> productWithQuantityList, HttpServletRequest request) {
        List<ProductWithQuantity> savedProductWithQuantityList = new ArrayList<>();
        for(ProductWithQuantity productWithQuantity : productWithQuantityList){
            Product product = productRepository.save(toProduct(productWithQuantity));
            Integer quantity = (Integer) inventoryClient.addStock(new StockRequest(product.getId(), productWithQuantity.quantity()), request).getBody();
            savedProductWithQuantityList.add(toProductWithQuantity(product, quantity));
        }
        return savedProductWithQuantityList;
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
