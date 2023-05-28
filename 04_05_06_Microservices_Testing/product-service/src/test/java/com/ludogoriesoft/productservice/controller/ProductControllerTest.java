package com.ludogoriesoft.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ludogoriesoft.productservice.dto.ProductDTO;
import com.ludogoriesoft.productservice.entity.Product;
import com.ludogoriesoft.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setName("tralala");
    }

    @Test
    void getAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        mockMvc.perform(get("/api/v1/products/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("tralala"));
    }

    @Test
    void findProductByIdWhenProductExists() throws Exception {
        when(productService.findProductById(1L)).thenReturn(productDTO);
        mockMvc.perform(get("/api/v1/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tralala"));
    }

    @Test
    void findProductByIdWhenProductNotExists() throws Exception {
        when(productService.findProductById(2L)).thenReturn(null);
        mockMvc.perform(get("/api/v1/products/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct() throws Exception {
        Product product = new Product();
        product.setName("tralala");
        product.setId(1L);
        when(productService.createProduct(productDTO)).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"tralala\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateProductWhenProductNotExist() throws Exception {

            when(productService.updateProduct(2L, productDTO)).thenReturn(null);

            mockMvc.perform(put("/api/v1/inventory/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\": \"tralala\"}"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

    }

    @Test
    void updateProductWhenProductExists() throws Exception {

        ProductDTO responseDTO = new ProductDTO();
        responseDTO.setName("tralala");
        responseDTO.setId(1L);
        when(productService.updateProduct(1L, productDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"tralala\"}"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void deleteProductWhenProductExists() throws Exception {
        when(productService.deleteProduct(1L)).thenReturn(productDTO);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deleteProductWhenProductNotExists() throws Exception {
        when(productService.deleteProduct(7L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/products/7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}