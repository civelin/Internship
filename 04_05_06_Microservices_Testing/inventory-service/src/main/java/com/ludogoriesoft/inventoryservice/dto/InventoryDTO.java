package com.ludogoriesoft.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
}
