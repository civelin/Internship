package com.ludogoriesoft.inventoryservice.configuration;

import com.ludogoriesoft.inventoryservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service/api/v1/products")
public interface ProductClient {

    @GetMapping
    ResponseEntity<List<ProductDTO>> getAllProducts();

    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> findProductById(@PathVariable("id") Long id);

}