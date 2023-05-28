package com.ludogoriesoft.inventoryservice.controller;

import com.ludogoriesoft.inventoryservice.dto.InventoryDTO;
import com.ludogoriesoft.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
public class InventoryController {


    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDTO> findInventoryByProductId(@PathVariable Long productId) {
        InventoryDTO inventoryDTO = inventoryService.findInventoryByProductId(productId);

        if (inventoryDTO != null) {
            return ResponseEntity.ok(inventoryDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long productId,
                                                        @Valid @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO inventory = inventoryService.updateInventoryByProductId(productId, inventoryDTO);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@Valid @RequestBody InventoryDTO inventoryDTO,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        InventoryDTO inventory = inventoryService.createInventory(inventoryDTO);

        if (inventory != null) {
            URI location = uriComponentsBuilder.path("/api/v1/inventory/{id}").
                    buildAndExpand(inventory.getId()).toUri();

            return ResponseEntity.
                    created(location).
                    build();
        }

        return ResponseEntity.notFound().build();
    }
}
