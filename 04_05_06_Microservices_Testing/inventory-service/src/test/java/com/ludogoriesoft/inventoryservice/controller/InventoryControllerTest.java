package com.ludogoriesoft.inventoryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ludogoriesoft.inventoryservice.dto.InventoryDTO;
import com.ludogoriesoft.inventoryservice.service.InventoryService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(InventoryController.class)
class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InventoryService inventoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private InventoryDTO inventoryDTO;


    @BeforeEach
    void setUp() {
        inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductId(2L);
        inventoryDTO.setQuantity(100);
    }


    @Test
    public void findInventoryByProductIdWhenProductExists() throws Exception {
        when(inventoryService.findInventoryByProductId(2L)).thenReturn(inventoryDTO);
        mockMvc.perform(get("/api/v1/inventory/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void findInventoryByProductIdWhenProductDoesNotExist() throws Exception {
        when(inventoryService.findInventoryByProductId(1L)).thenReturn(null);
        mockMvc.perform(get("/api/v1/inventory/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateInventoryWhenProductExists() throws Exception {
        InventoryDTO inventoryDTO1 = new InventoryDTO();
        inventoryDTO1.setProductId(2L);
        inventoryDTO1.setQuantity(100);

        when(inventoryService.updateInventoryByProductId(2L, inventoryDTO1)).thenReturn(inventoryDTO);

        mockMvc.perform(put("/api/v1/inventory/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 2, \"quantity\": 100}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(100));
    }


    @Test
    void updateInventoryWhenProductDoesNotExist() throws Exception {
        InventoryDTO inventoryDTO1 = new InventoryDTO();
        inventoryDTO1.setProductId(2L);
        inventoryDTO1.setQuantity(100);

        when(inventoryService.updateInventoryByProductId(2L, inventoryDTO1)).thenReturn(null);

        mockMvc.perform(put("/api/v1/inventory/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 2, \"quantity\": 100}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void createInventoryWhenProductExistsInProductService() throws Exception {
        InventoryDTO responseDTO = new InventoryDTO();
        responseDTO.setId(3L);
        responseDTO.setProductId(2L);
        responseDTO.setQuantity(100);

        when(inventoryService.createInventory(inventoryDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 2, \"quantity\": 100}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createInventoryWhenProductDoesNotExistInProductService() throws Exception {

        when(inventoryService.createInventory(inventoryDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 2, \"quantity\": 100}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}