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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
class CarControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean private CarService carService;

  @Test
  void testGetAllCars() throws Exception {
    List<Car> cars = Arrays.asList(new Car(), new Car());
    when(carService.getAllCars()).thenReturn(cars);

    mockMvc.perform(get("/cars")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testGetCarById() throws Exception {
    long carId = 1L;
    Car car = new Car();
    when(carService.getCarById(carId)).thenReturn(car);

    mockMvc
        .perform(get("/cars/{id}", carId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(carId)));
  }

  @Test
  void testSaveCar() throws Exception {
    Car car = new Car();
    when(carService.saveCar(any())).thenReturn(car);

    mockMvc
        .perform(
            post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"make\": \"Toyota\", \"model\": \"Camry\" }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.make", is("Toyota")))
        .andExpect(jsonPath("$.model", is("Camry")));
  }

  @Test
  void testUpdateCar() throws Exception {
    long carId = 1L;
    Car updatedCar = new Car();
    when(carService.updateCar(eq(carId), any())).thenReturn(updatedCar);

    mockMvc
        .perform(
            put("/cars/update/{id}", carId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"make\": \"Honda\", \"model\": \"Accord\" }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.make", is("Honda")))
        .andExpect(jsonPath("$.model", is("Accord")));
  }

  @Test
  void testDeleteCar() throws Exception {
    long carId = 1L;

    mockMvc
        .perform(delete("/cars/{id}", carId))
        .andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(carId)));
  }

  @Test
  void testCreateText() throws Exception {
    String text = "Some text";
    Car car = new Car();
    when(carService.analyzeText(text)).thenReturn(car);

    mockMvc.perform(post("/cars/create").param("text", text)).andExpect(status().isCreated());
  }

  @Test
  void testAddOwnerToCar() throws Exception {
    long carId = 1L;
    long ownerId = 1L;

    mockMvc
        .perform(post("/cars/{carId}/owners/{ownerId}", carId, ownerId))
        .andExpect(status().isOk());
  }

  @Test
  void testRemoveOwnerFromCar() throws Exception {
    long carId = 1L;
    long ownerId = 1L;

    mockMvc
        .perform(delete("/cars/{carId}/owners/{ownerId}", carId, ownerId))
        .andExpect(status().isOk());
  }

  @Test
  void testUpdateCarOwners() throws Exception {
    long carId = 1L;
    List<Long> ownerIds = Arrays.asList(1L, 2L, 3L);

    mockMvc
        .perform(
            put("/cars/{carId}/owners", carId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1, 2, 3]"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetAllOwnersOfCar() throws Exception {
    long carId = 1L;
    List<Owner> owners = Arrays.asList(new Owner(), new Owner());
    when(carService.getAllOwnersOfCar(carId)).thenReturn(owners);

    mockMvc
        .perform(get("/cars/{carId}/owners", carId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testAddProductToCar() throws Exception {
    long carId = 1L;
    long productId = 1L;

    mockMvc
        .perform(post("/cars/{carId}/products/{productId}", carId, productId))
        .andExpect(status().isOk());
  }

  @Test
  void testRemoveProductFromCar() throws Exception {
    long carId = 1L;
    long productId = 1L;

    mockMvc
        .perform(delete("/cars/{carId}/products/{productId}", carId, productId))
        .andExpect(status().isOk());
  }

  @Test
  void testUpdateCarProducts() throws Exception {
    long carId = 1L;
    List<Long> productIds = Arrays.asList(1L, 2L, 3L);

    mockMvc
        .perform(
            put("/cars/{carId}/products", carId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1, 2, 3]"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetAllProductsOfCar() throws Exception {
    long carId = 1L;
    List<Product> products = Arrays.asList(new Product(), new Product());
    when(carService.getAllProductsOfCar(carId)).thenReturn(products);

    mockMvc
        .perform(get("/cars/{carId}/products", carId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }
}
