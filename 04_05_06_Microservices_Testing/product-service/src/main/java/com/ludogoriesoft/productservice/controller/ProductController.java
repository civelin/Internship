package com.ludogoriesoft.productservice.controller;


import com.ludogoriesoft.productservice.dto.ProductDTO;
import com.ludogoriesoft.productservice.entity.Product;
import com.ludogoriesoft.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        ProductDTO product = productService.findProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        Product product = productService.createProduct(productDTO);

        URI location = uriComponentsBuilder.path("/api/v1/products/{id}").
                buildAndExpand(product.getId()).toUri();

        return ResponseEntity.
                created(location).
                build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO product = productService.updateProduct(id, productDTO);
        if (product != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        ProductDTO productDTO = productService.deleteProduct(id);

        if (productDTO != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}