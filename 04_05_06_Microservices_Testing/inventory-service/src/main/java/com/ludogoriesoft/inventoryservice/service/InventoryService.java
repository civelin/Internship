package com.ludogoriesoft.inventoryservice.service;

import com.ludogoriesoft.inventoryservice.configuration.ProductClient;
import com.ludogoriesoft.inventoryservice.dto.InventoryDTO;
import com.ludogoriesoft.inventoryservice.entity.Inventory;
import com.ludogoriesoft.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    private final ProductClient productClient;

    public InventoryDTO findInventoryByProductId(Long productId){

        Optional<Inventory> inventory = inventoryRepository.findByproductId(productId);
        return inventory.map(value -> modelMapper.map(value, InventoryDTO.class)).orElse(null);
    }

    public InventoryDTO updateInventoryByProductId(Long productId, InventoryDTO inventoryDTO){
        Optional<Inventory> inventory = inventoryRepository.findByproductId(productId);

        if(inventory.isPresent()){
            inventory.get().setQuantity(inventoryDTO.getQuantity());
            inventoryRepository.save(inventory.get());
            return modelMapper.map(inventory.get(), InventoryDTO.class);
        }
        return null;
    }

    public InventoryDTO createInventory(InventoryDTO inventoryDTO){
        if(productClient.findProductById(inventoryDTO.getProductId()).getStatusCode().is2xxSuccessful() && inventoryRepository.findByproductId(inventoryDTO.getProductId()).isEmpty()){
            return modelMapper.map(inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class)), InventoryDTO.class);
        }
        return null;
    }
}
