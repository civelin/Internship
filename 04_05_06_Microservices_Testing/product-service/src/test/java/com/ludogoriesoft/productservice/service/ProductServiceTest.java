package com.ludogoriesoft.productservice.service;

import com.ludogoriesoft.productservice.dto.ProductDTO;
import com.ludogoriesoft.productservice.entity.Product;
import com.ludogoriesoft.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService serviceToTest;

    @Mock
    private  ProductRepository  productRepository;
    @Mock
    private ModelMapper modelMapper;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("tralala");

        productDTO = new ProductDTO();
        productDTO.setName("tralala");
        productDTO.setId(1L);

    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        Assertions.assertEquals(serviceToTest.getAllProducts(), Collections.singletonList(productDTO));
        }

    @Test
    void findProductByIdWhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        Assertions.assertEquals(serviceToTest.findProductById(1L), productDTO);
    }

    @Test
    void findProductByIdWhenProductNotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(serviceToTest.findProductById(1L));
    }

    @Test
    void createProduct() {
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Assertions.assertEquals(serviceToTest.createProduct(productDTO), product);
    }

    @Test
    void updateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        Assertions.assertEquals(serviceToTest.updateProduct(1L, productDTO), productDTO);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProductWhenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(serviceToTest.updateProduct(1L, productDTO));
        verify(productRepository, times(0)).save(product);
        verify(modelMapper, times(0)).map(product, ProductDTO.class);

    }

    @Test
    void deleteProductWhenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(serviceToTest.deleteProduct(1L));
        verify(productRepository, times(0)).deleteById(1L);
    }

    @Test
    void deleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        Assertions.assertEquals(serviceToTest.deleteProduct(1L), productDTO);
        verify(productRepository, times(1)).deleteById(1L);
    }
}