package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
 class CarServiceTest {

  @Mock
  private CarDao carDao;

  @Mock
  private ProductDao productDao;

  @Mock
  private OwnerDao ownerDao;

  @InjectMocks
  private CarService carService;

  @Test
  void testGetAllCars() {
    List<Car> expectedCars = Arrays.asList(new Car(), new Car());
    when(carDao.getAllCars()).thenReturn(expectedCars);

    List<Car> result = carService.getAllCars();

    assertEquals(expectedCars, result);
  }

  @Test
  void testGetCarById() {
    long carId = 1L;
    Car expectedCar = new Car();
    when(carDao.getCarById(carId)).thenReturn(expectedCar);

    Car result = carService.getCarById(carId);

    assertEquals(expectedCar, result);
  }

  @Test
  void testSaveCar() {
    Car carToSave = new Car();
    when(carDao.saveCar(carToSave)).thenReturn(carToSave);

    Car result = carService.saveCar(carToSave);

    assertEquals(carToSave, result);
  }

  @Test
  void testUpdateCar() {
    long carId = 1L;
    Car carToUpdate = new Car();
    when(carDao.getCarById(carId)).thenReturn(carToUpdate);

    Car result = carService.updateCar(carId, carToUpdate);

    assertEquals(carToUpdate, result);
  }

  @Test
  void testDeleteCar() {
    long carId = 1L;

    carService.deleteCar(carId);

    verify(carDao).deleteCar(carId);
  }

  @Test
  void testAnalyzeText() {
    String text = "VIN: 123456789\nБренд: Toyota\nМодель: Camry\nГод: 2020";

    Car result = carService.analyzeText(text);

    assertNotNull(result);
    assertEquals("123456789", result.getVin());
    assertEquals("Toyota", result.getMake());
    assertEquals("Camry", result.getModel());
    assertEquals(2020, result.getYear());
  }

  @Test
  void testAddOwnerToCar() {
    long carId = 1L;
    long ownerId = 1L;
    Car car = new Car();
    Owner owner = new Owner();
    when(carDao.getCarById(carId)).thenReturn(car);
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

    carService.addOwnerToCar(carId, ownerId);

    assertTrue(car.getOwners().contains(owner));
    assertTrue(owner.getCars().contains(car));
  }

  @Test
  void testRemoveOwnerFromCar() {
    long carId = 1L;
    long ownerId = 1L;
    Car car = new Car();
    Owner owner = new Owner();
    car.getOwners().add(owner);
    when(carDao.getCarById(carId)).thenReturn(car);

    carService.removeOwnerFromCar(carId, ownerId);

    assertFalse(car.getOwners().contains(owner));
  }

  @Test
  void testUpdateCarOwners() {
    long carId = 1L;
    long ownerId1 = 1L;
    long ownerId2 = 2L;
    Car car = new Car();
    Owner owner1 = new Owner();
    Owner owner2 = new Owner();
    when(carDao.getCarById(carId)).thenReturn(car);
    when(ownerDao.getOwnerById(ownerId1)).thenReturn(owner1);
    when(ownerDao.getOwnerById(ownerId2)).thenReturn(owner2);

    carService.updateCarOwners(carId, Arrays.asList(ownerId1, ownerId2));

    assertTrue(car.getOwners().contains(owner1));
    assertTrue(car.getOwners().contains(owner2));
  }

  @Test
  void testGetAllOwnersOfCar() {
    long carId = 1L;
    Car car = new Car();
    Owner owner1 = new Owner();
    Owner owner2 = new Owner();
    car.getOwners().add(owner1);
    car.getOwners().add(owner2);
    when(carDao.getCarById(carId)).thenReturn(car);

    List<Owner> result = carService.getAllOwnersOfCar(carId);

    assertEquals(2, result.size());
  }

  @Test
  void testAddProductToCar() {
    long carId = 1L;
    long productId = 1L;
    Car car = new Car();
    Product product = new Product();
    when(carDao.getCarById(carId)).thenReturn(car);
    when(productDao.getProductById(productId)).thenReturn(product);

    carService.addProductToCar(carId, productId);

    assertTrue(car.getProducts().contains(product));
    assertEquals(car, product.getCar());
  }

  @Test
  void testRemoveProductFromCar() {
    long carId = 1L;
    long productId = 1L;
    Car car = new Car();
    Product product = new Product();
    car.getProducts().add(product);
    when(carDao.getCarById(carId)).thenReturn(car);

    carService.removeProductFromCar(carId, productId);

    assertFalse(car.getProducts().contains(product));
  }

  @Test
  void testUpdateCarProducts() {
    long carId = 1L;
    long productId1 = 1L;
    long productId2 = 2L;
    Car car = new Car();
    Product product1 = new Product();
    Product product2 = new Product();
    when(carDao.getCarById(carId)).thenReturn(car);
    when(productDao.getProductById(productId1)).thenReturn(product1);
    when(productDao.getProductById(productId2)).thenReturn(product2);

    carService.updateCarProducts(carId, Arrays.asList(productId1, productId2));

    assertTrue(car.getProducts().contains(product1));
    assertTrue(car.getProducts().contains(product2));
  }

  @Test
  void testGetAllProductsOfCar() {
    long carId = 1L;
    Car car = new Car();
    Product product1 = new Product();
    Product product2 = new Product();
    car.getProducts().add(product1);
    car.getProducts().add(product2);
    when(carDao.getCarById(carId)).thenReturn(car);

    List<Product> result = carService.getAllProductsOfCar(carId);

    assertEquals(2, result.size());
  }
}

