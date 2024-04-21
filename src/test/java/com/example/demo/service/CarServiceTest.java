package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.repository.CarRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarDao carDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private OwnerDao ownerDao;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carDao.getAllCars()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertEquals(2, result.size());
        verify(carDao, times(1)).getAllCars();
    }

    @Test
    public void testGetCarById() {
        Car car = new Car();
        car.setId(1L);
        when(carDao.getCarById(1L)).thenReturn(car);

        Car result = carService.getCarById(1L);

        assertEquals(1L, result.getId());
        verify(carDao, times(1)).getCarById(1L);
    }

    @Test
    public void testSaveCar() {
        Car car = new Car();
        when(carDao.saveCar(car)).thenReturn(car);

        Car result = carService.saveCar(car);

        assertNotNull(result);
        verify(carDao, times(1)).saveCar(car);
    }

    @Test
    public void testUpdateCar_Found() {
        Car existingCar = new Car();
        existingCar.setId(1L);
        existingCar.setOwners(new ArrayList<>()); // Убедимся, что список не null
        existingCar.setProducts(new ArrayList<>()); // Убедимся, что список не null

        Car updateCar = new Car();
        updateCar.setVin("NEWVIN");

        when(carDao.getCarById(1L)).thenReturn(existingCar);
        when(carDao.saveCar(existingCar)).thenReturn(existingCar);

        Car result = carService.updateCar(1L, updateCar);

        assertEquals("NEWVIN", result.getVin());
        verify(carDao, times(1)).saveCar(existingCar);
    }

    @Test
    public void testUpdateCar_NotFound() {
        Car updateCar = new Car();
        when(carDao.getCarById(1L)).thenReturn(null);

        Car result = carService.updateCar(1L, updateCar);

        assertNull(result);
        verify(carDao, times(1)).getCarById(1L);
    }

    @Test
    public void testDeleteCar() {
        doNothing().when(carDao).deleteCar(1L);

        carService.deleteCar(1L);

        verify(carDao, times(1)).deleteCar(1L);
    }

    @Test
    public void testAnalyzeText() {
        String text = "VIN: ABC123 Brand: Toyota Model: Camry Year: 2021";

        Car expectedCar = new Car();
        expectedCar.setVin("ABC123");
        expectedCar.setMake("Toyota");
        expectedCar.setModel("Camry");
        expectedCar.setYear(2021);

        when(carRepository.save(any(Car.class))).thenReturn(expectedCar);

        Car result = carService.analyzeText(text);

        assertNotNull(result);
        assertEquals("ABC123", result.getVin());
        assertEquals("Toyota", result.getMake());
        assertEquals("Camry", result.getModel());
        assertEquals(2021, result.getYear());
    }

    @Test
    public void testAddOwnerToCar() {
        Car car = new Car();
        car.setId(1L);
        car.setOwners(new ArrayList<>());  // Убедитесь, что список не null

        Owner owner = new Owner();
        owner.setId(1L);
        owner.setCars(new ArrayList<>());  // Убедитесь, что список не null

        when(carDao.getCarById(1L)).thenReturn(car);
        when(ownerDao.getOwnerById(1L)).thenReturn(owner);

        carService.addOwnerToCar(1L, 1L);

        assertTrue(car.getOwners().contains(owner));  // Проверяем добавление
        assertTrue(owner.getCars().contains(car));  // Проверяем добавление в Owner
        verify(carDao, times(1)).saveCar(car);  // Убедитесь, что метод сохранения вызван
    }

    @Test
    public void testRemoveOwnerFromCar() {
        Car car = new Car();
        car.setId(1L);
        if (car.getOwners() == null) {
            car.setOwners(new ArrayList<>());
        }
        Owner owner = new Owner();
        owner.setId(1L);

        car.getOwners().add(owner);

        when(carDao.getCarById(1L)).thenReturn(car);

        carService.removeOwnerFromCar(1L, 1L);

        assertFalse(car.getOwners().contains(owner));
        verify(carDao, times(1)).saveCar(car);
    }

    @Test
    public void testAddProductToCar() {
        Car car = new Car();
        car.setId(1L);
        if (car.getProducts() == null) {
            car.setProducts(new ArrayList<>());
        }
        Product product = new Product();
        product.setId(1L);

        when(carDao.getCarById(1L)).thenReturn(car);
        when(productDao.getProductById(1L)).thenReturn(product);

        carService.addProductToCar(1L, 1L);

        assertTrue(car.getProducts().contains(product));
        verify(carDao, times(1)).saveCar(car);
    }

    @Test
    public void testRemoveProductFromCar() {
        Car car = new Car();
        car.setId(1L);
        if (car.getProducts() == null) {
            car.setProducts(new ArrayList<>());
        }
        Product product = new Product();
        product.setId(1L);

        car.getProducts().add(product);

        when(carDao.getCarById(1L)).thenReturn(car);

        carService.removeProductFromCar(1L, 1L);

        assertFalse(car.getProducts().contains(product));
        verify(carDao, times(1)).saveCar(car);
    }

    @Test
    public void testGetAllOwnersOfCar() {
        Car car = new Car();
        car.setId(1L);
        if (car.getOwners() == null) {
            car.setOwners(new ArrayList<>());
        }

        Owner owner = new Owner();
        owner.setId(1L);

        car.getOwners().add(owner);

        when(carDao.getCarById(1L)).thenReturn(car);

        List<Owner> owners = carService.getAllOwnersOfCar(1L);

        assertEquals(1, owners.size());
        verify(carDao, times(1)).getCarById(1L);
    }

    @Test
    public void testUpdateCarOwners() {
        Car car = new Car();
        car.setId(1L);
        List<Long> ownerIds = Arrays.asList(1L, 2L);

        Owner owner1 = new Owner();
        owner1.setId(1L);

        Owner owner2 = new Owner();
        owner2.setId(2L);

        when(carDao.getCarById(1L)).thenReturn(car);
        when(ownerDao.getOwnerById(1L)).thenReturn(owner1);
        when(ownerDao.getOwnerById(2L)).thenReturn(owner2);

        if (car.getOwners() == null) {
            car.setOwners(new ArrayList<>());
        }

        carService.updateCarOwners(1L, ownerIds);

        assertTrue(car.getOwners().contains(owner1));
        assertTrue(car.getOwners().contains(owner2));
        verify(carDao, times(1)).saveCar(car);
    }

    @Test
    public void testUpdateCarProducts() {
        Car car = new Car();
        car.setId(1L);
        List<Long> productIds = Arrays.asList(1L, 2L);

        Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        when(carDao.getCarById(1L)).thenReturn(car);
        when(productDao.getProductById(1L)).thenReturn(product1);
        when(productDao.getProductById(2L)).thenReturn(product2);

        if (car.getProducts() == null) {
            car.setProducts(new ArrayList<>());
        }

        carService.updateCarProducts(1L, productIds);

        assertTrue(car.getProducts().contains(product1));
        assertTrue(car.getProducts().contains(product2));
        verify(carDao, times(1)).saveCar(car);
    }
}
