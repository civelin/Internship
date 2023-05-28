package com.ludogoriesoft.productservice.service;

import com.ludogoriesoft.productservice.dto.ProductDTO;
import com.ludogoriesoft.productservice.entity.Product;
import com.ludogoriesoft.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public List<ProductDTO> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public ProductDTO findProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return modelMapper.map(product.get(), ProductDTO.class);
        }
        return null;
    }

    public Product createProduct(ProductDTO productDTO){
        return productRepository.save(modelMapper.map(productDTO, Product.class));
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            product.get().setName(productDTO.getName());
            productRepository.save(product.get());
            return modelMapper.map(product.get(), ProductDTO.class);
        }
        return null;
    }

    public ProductDTO deleteProduct(Long id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            productRepository.deleteById(id);
            return modelMapper.map(product.get(), ProductDTO.class);
        }

        return null;
    }
}


