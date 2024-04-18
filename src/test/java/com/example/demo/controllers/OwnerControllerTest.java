package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OwnerController.class)
 class OwnerControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
     void testGetAllOwners() throws Exception {
        // Arrange
        List<Owner> owners = Arrays.asList(new Owner(), new Owner());
        when(ownerService.getAllOwners()).thenReturn(owners);

        // Act and Assert
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
     void testGetOwnerById() throws Exception {
        // Arrange
        long ownerId = 1L;
        Owner owner = new Owner();
        when(ownerService.getOwnerById(ownerId)).thenReturn(owner);

        // Act and Assert
        mockMvc.perform(get("/owners/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ownerId)));
    }

    @Test
     void testCreateOwner() throws Exception {
        // Arrange
        Owner owner = new Owner();
        when(ownerService.saveOwner(any())).thenReturn(owner);

        // Act and Assert
        mockMvc.perform(post("/owners/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John Doe\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
     void testUpdateOwner() throws Exception {
        // Arrange
        long ownerId = 1L;
        Owner updatedOwner = new Owner();
        when(ownerService.updateOwner(eq(ownerId), any())).thenReturn(updatedOwner);

        // Act and Assert
        mockMvc.perform(put("/owners/{id}", ownerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Jane Doe\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Jane Doe")));
    }

    @Test
     void testDeleteOwner() throws Exception {
        // Arrange
        long ownerId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/owners/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(ownerId)));
    }

    @Test
     void testGetAllProductsByOwnerId() throws Exception {
        // Arrange
        long ownerId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(ownerService.getAllProductsByOwnerId(ownerId)).thenReturn(products);

        // Act and Assert
        mockMvc.perform(get("/owners/{ownerId}/products", ownerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
     void testSaveProductForOwner() throws Exception {
        // Arrange
        long ownerId = 1L;
        Product product = new Product();
        when(ownerService.saveProductForOwner(eq(ownerId), any())).thenReturn(product);

        // Act and Assert
        mockMvc.perform(post("/owners/{ownerId}/products", ownerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Product\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product")));
    }

    @Test
     void testAddCarToOwner() throws Exception {
        // Arrange
        long ownerId = 1L;
        long carId = 1L;

        // Act and Assert
        mockMvc.perform(post("/owners/{ownerId}/cars/{carId}", ownerId, carId))
                .andExpect(status().isOk());
    }

    @Test
     void testRemoveCarFromOwner() throws Exception {
        // Arrange
        long ownerId = 1L;
        long carId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/owners/{ownerId}/cars/{carId}", ownerId, carId))
                .andExpect(status().isOk());
    }

    @Test
     void testUpdateOwnerCars() throws Exception {
        // Arrange
        long ownerId = 1L;
        List<Long> carIds = Arrays.asList(1L, 2L, 3L);

        // Act and Assert
        mockMvc.perform(put("/owners/{ownerId}/cars", ownerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2, 3]"))
                .andExpect(status().isOk());
    }

    @Test
     void testGetAllCarsOfOwner() throws Exception {
        // Arrange
        long ownerId = 1L;
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(ownerService.getAllCarsOfOwner(ownerId)).thenReturn(cars);

        // Act and Assert
        mockMvc.perform(get("/owners/{ownerId}/cars", ownerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
     void testAddSeveralOwners() throws Exception {
        // Arrange
        List<Owner> owners = Arrays.asList(new Owner(), new Owner());
        when(ownerService.saveOwner(any())).thenReturn(new Owner());

        // Act and Assert
        mockMvc.perform(post("/owners/addSeveralOwners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{ \"name\": \"John\" }, { \"name\": \"Jane\" }]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
