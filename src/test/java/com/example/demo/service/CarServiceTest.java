package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarDao carDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private OwnerDao ownerDao;

    @Mock
    private CacheService cacheService;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void testGetAllCars() {
        // Arrange
        List<Car> expectedCars = Arrays.asList(new Car(), new Car());
        when(carDao.getAllCars()).thenReturn(expectedCars);

        // Act
        List<Car> result = carService.getAllCars();

        // Assert
        assertEquals(expectedCars, result);
    }

    @Test
    public void testGetCarById() {
        // Arrange
        long carId = 1L;
        Car expectedCar = new Car();
        when(carDao.getCarById(carId)).thenReturn(expectedCar);

        // Act
        Car result = carService.getCarById(carId);

        // Assert
        assertEquals(expectedCar, result);
    }

    @Test
    public void testSaveCar() {
        // Arrange
        Car carToSave = new Car();
        when(carDao.saveCar(carToSave)).thenReturn(carToSave);

        // Act
        Car result = carService.saveCar(carToSave);

        // Assert
        assertEquals(carToSave, result);
    }

    @Test
    public void testUpdateCar() {
        // Arrange
        long carId = 1L;
        Car carToUpdate = new Car();
        when(carDao.getCarById(carId)).thenReturn(carToUpdate);

        // Act
        Car result = carService.updateCar(carId, carToUpdate);

        // Assert
        assertEquals(carToUpdate, result);
    }

    @Test
    public void testDeleteCar() {
        // Arrange
        long carId = 1L;

        // Act
        carService.deleteCar(carId);

        // Assert
        verify(carDao).deleteCar(carId);
    }

    @Test
    public void testAnalyzeText() {
        // Arrange
        String text = "VIN: 123456789\nБренд: Toyota\nМодель: Camry\nГод: 2020";

        // Act
        Car result = carService.analyzeText(text);

        // Assert
        assertNotNull(result);
        assertEquals("123456789", result.getVin());
        assertEquals("Toyota", result.getMake());
        assertEquals("Camry", result.getModel());
        assertEquals(2020, result.getYear());
    }

    @Test
    public void testAddOwnerToCar() {
        // Arrange
        long carId = 1L;
        long ownerId = 1L;
        Car car = new Car();
        Owner owner = new Owner();
        when(carDao.getCarById(carId)).thenReturn(car);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

        // Act
        carService.addOwnerToCar(carId, ownerId);

        // Assert
        assertTrue(car.getOwners().contains(owner));
        assertTrue(owner.getCars().contains(car));
    }

    @Test
    public void testRemoveOwnerFromCar() {
        // Arrange
        long carId = 1L;
        long ownerId = 1L;
        Car car = new Car();
        Owner owner = new Owner();
        car.getOwners().add(owner);
        when(carDao.getCarById(carId)).thenReturn(car);

        // Act
        carService.removeOwnerFromCar(carId, ownerId);

        // Assert
        assertFalse(car.getOwners().contains(owner));
    }

    @Test
    public void testUpdateCarOwners() {
        // Arrange
        long carId = 1L;
        long ownerId1 = 1L;
        long ownerId2 = 2L;
        Car car = new Car();
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        when(carDao.getCarById(carId)).thenReturn(car);
        when(ownerDao.getOwnerById(ownerId1)).thenReturn(owner1);
        when(ownerDao.getOwnerById(ownerId2)).thenReturn(owner2);

        // Act
        carService.updateCarOwners(carId, Arrays.asList(ownerId1, ownerId2));

        // Assert
        assertTrue(car.getOwners().contains(owner1));
        assertTrue(car.getOwners().contains(owner2));
    }

    @Test
    public void testGetAllOwnersOfCar() {
        // Arrange
        long carId = 1L;
        Car car = new Car();
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        car.getOwners().add(owner1);
        car.getOwners().add(owner2);
        when(carDao.getCarById(carId)).thenReturn(car);

        // Act
        List<Owner> result = carService.getAllOwnersOfCar(carId);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testAddProductToCar() {
        // Arrange
        long carId = 1L;
        long productId = 1L;
        Car car = new Car();
        Product product = new Product();
        when(carDao.getCarById(carId)).thenReturn(car);
        when(productDao.getProductById(productId)).thenReturn(product);

        // Act
        carService.addProductToCar(carId, productId);

        // Assert
        assertTrue(car.getProducts().contains(product));
        assertEquals(car, product.getCar());
    }

    @Test
    public void testRemoveProductFromCar() {
        // Arrange
        long carId = 1L;
        long productId = 1L;
        Car car = new Car();
        Product product = new Product();
        car.getProducts().add(product);
        when(carDao.getCarById(carId)).thenReturn(car);

        // Act
        carService.removeProductFromCar(carId, productId);

        // Assert
        assertFalse(car.getProducts().contains(product));
    }

    @Test
    public void testUpdateCarProducts() {
        // Arrange
        long carId = 1L;
        long productId1 = 1L;
        long productId2 = 2L;
        Car car = new Car();
        Product product1 = new Product();
        Product product2 = new Product();
        when(carDao.getCarById(carId)).thenReturn(car);
        when(productDao.getProductById(productId1)).thenReturn(product1);
        when(productDao.getProductById(productId2)).thenReturn(product2);

        // Act
        carService.updateCarProducts(carId, Arrays.asList(productId1, productId2));

        // Assert
        assertTrue(car.getProducts().contains(product1));
        assertTrue(car.getProducts().contains(product2));
    }

    @Test
    public void testGetAllProductsOfCar() {
        // Arrange
        long carId = 1L;
        Car car = new Car();
        Product product1 = new Product();
        Product product2 = new Product();
        car.getProducts().add(product1);
        car.getProducts().add(product2);
        when(carDao.getCarById(carId)).thenReturn(car);

        // Act
        List<Product> result = carService.getAllProductsOfCar(carId);

        // Assert
        assertEquals(2, result.size());
    }
}

