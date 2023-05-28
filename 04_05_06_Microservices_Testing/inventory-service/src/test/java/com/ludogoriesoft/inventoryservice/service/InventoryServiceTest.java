package com.ludogoriesoft.inventoryservice.service;

import com.ludogoriesoft.inventoryservice.configuration.ProductClient;
import com.ludogoriesoft.inventoryservice.dto.InventoryDTO;
import com.ludogoriesoft.inventoryservice.dto.ProductDTO;
import com.ludogoriesoft.inventoryservice.entity.Inventory;
import com.ludogoriesoft.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {
    @InjectMocks
    private InventoryService serviceToTest;
    @Mock
    private InventoryRepository  inventoryRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProductClient productClient;

    private InventoryDTO inventoryDTO;
    private Inventory inventory;


    @BeforeEach
    void init(){
        inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductId(1L);
        inventoryDTO.setQuantity(10);

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductId(1L);
        inventory.setQuantity(10);
    }

    @Test
    void findInventoryByProductIdWhenProductExists() {
        when(inventoryRepository.findByproductId(1L)).thenReturn(Optional.of(inventory));
       when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

        Assertions.assertEquals(serviceToTest.findInventoryByProductId(1L), inventoryDTO);

    }

    @Test
    void findInventoryByProductIdWhenProductsNotExists() {
        when(inventoryRepository.findByproductId(1L)).thenReturn(Optional.empty());
        assertNull(serviceToTest.findInventoryByProductId(1L));

    }

    @Test
    void updateInventoryByProductId() {
        when(inventoryRepository.findByproductId(1L)).thenReturn(Optional.of(inventory));
        when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

        Assertions.assertEquals(serviceToTest.updateInventoryByProductId(1L,inventoryDTO), inventoryDTO);
    }

    @Test
    void updateInventoryByProductIdWhenProductDoesNotExist() {
        when(inventoryRepository.findByproductId(1L)).thenReturn(Optional.empty());

        assertNull(serviceToTest.updateInventoryByProductId(1L, inventoryDTO));
    }

    @Test
    void createInventoryWhenProductExistsInProductService() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("tralala");
        ResponseEntity<ProductDTO> responseEntity = ResponseEntity.ok(productDTO);

        when(productClient.findProductById(1L)).thenReturn(responseEntity);
        when(modelMapper.map(inventoryDTO, Inventory.class)).thenReturn(inventory);
        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

        Assertions.assertEquals(serviceToTest.createInventory(inventoryDTO), inventoryDTO);
    }

    @Test
    void createInventoryWhenProductNotExistsInProductService() {

        ResponseEntity<ProductDTO> responseEntity = ResponseEntity.notFound().build();
        when(productClient.findProductById(1L)).thenReturn(responseEntity);


        assertNull(serviceToTest.createInventory(inventoryDTO));
    }
}