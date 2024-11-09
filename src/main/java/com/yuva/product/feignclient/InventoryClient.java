package com.yuva.product.feignclient;

import com.yuva.product.dto.StockRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="inventory-service", url = "http://inventory-service:8084")
public interface InventoryClient {
    @PostMapping("inventory/addStock")
    Integer addStock(@RequestBody StockRequest stockRequest);
}
