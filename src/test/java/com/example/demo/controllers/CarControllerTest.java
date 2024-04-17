package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testGetAllCars() throws Exception {
        // Arrange
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.getAllCars()).thenReturn(cars);

        // Act and Assert
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetCarById() throws Exception {
        // Arrange
        long carId = 1L;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        // Act and Assert
        mockMvc.perform(get("/cars/{id}", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(carId)));
    }

    @Test
    public void testSaveCar() throws Exception {
        // Arrange
        Car car = new Car();
        when(carService.saveCar(any())).thenReturn(car);

        // Act and Assert
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"make\": \"Toyota\", \"model\": \"Camry\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Camry")));
    }

    @Test
    public void testUpdateCar() throws Exception {
        // Arrange
        long carId = 1L;
        Car updatedCar = new Car();
        when(carService.updateCar(eq(carId), any())).thenReturn(updatedCar);

        // Act and Assert
        mockMvc.perform(put("/cars/update/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"make\": \"Honda\", \"model\": \"Accord\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make", is("Honda")))
                .andExpect(jsonPath("$.model", is("Accord")));
    }

    @Test
    public void testDeleteCar() throws Exception {
        // Arrange
        long carId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/cars/{id}", carId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(carId)));
    }

    @Test
    public void testCreateText() throws Exception {
        // Arrange
        String text = "Some text";
        Car car = new Car();
        when(carService.analyzeText(text)).thenReturn(car);

        // Act and Assert
        mockMvc.perform(post("/cars/create")
                        .param("text", text))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddOwnerToCar() throws Exception {
        // Arrange
        long carId = 1L;
        long ownerId = 1L;

        // Act and Assert
        mockMvc.perform(post("/cars/{carId}/owners/{ownerId}", carId, ownerId))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveOwnerFromCar() throws Exception {
        // Arrange
        long carId = 1L;
        long ownerId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/cars/{carId}/owners/{ownerId}", carId, ownerId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCarOwners() throws Exception {
        // Arrange
        long carId = 1L;
        List<Long> ownerIds = Arrays.asList(1L, 2L, 3L);

        // Act and Assert
        mockMvc.perform(put("/cars/{carId}/owners", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2, 3]"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllOwnersOfCar() throws Exception {
        // Arrange
        long carId = 1L;
        List<Owner> owners = Arrays.asList(new Owner(), new Owner());
        when(carService.getAllOwnersOfCar(carId)).thenReturn(owners);

        // Act and Assert
        mockMvc.perform(get("/cars/{carId}/owners", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testAddProductToCar() throws Exception {
        // Arrange
        long carId = 1L;
        long productId = 1L;

        // Act and Assert
        mockMvc.perform(post("/cars/{carId}/products/{productId}", carId, productId))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveProductFromCar() throws Exception {
        // Arrange
        long carId = 1L;
        long productId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/cars/{carId}/products/{productId}", carId, productId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCarProducts() throws Exception {
        // Arrange
        long carId = 1L;
        List<Long> productIds = Arrays.asList(1L, 2L, 3L);

        // Act and Assert
        mockMvc.perform(put("/cars/{carId}/products", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2, 3]"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllProductsOfCar() throws Exception {
        // Arrange
        long carId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(carService.getAllProductsOfCar(carId)).thenReturn(products);

        // Act and Assert
        mockMvc.perform(get("/cars/{carId}/products", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
