package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerDao ownerDao;

    @Mock
    private CarDao carDao;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    public void testGetAllOwners() {
        // Arrange
        List<Owner> owners = new ArrayList<>();
        owners.add(new Owner());
        owners.add(new Owner());
        when(ownerDao.getAllOwners()).thenReturn(owners);

        // Act
        List<Owner> result = ownerService.getAllOwners();

        // Assert
        assertEquals(owners.size(), result.size());
    }

    @Test
    public void testGetOwnerById_Exists() {
        // Arrange
        long ownerId = 1L;
        Owner owner = new Owner();
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

        // Act
        Owner result = ownerService.getOwnerById(ownerId);

        // Assert
        assertEquals(owner, result);
    }

    @Test
    public void testGetOwnerById_NotExists() {
        // Arrange
        long ownerId = 1L;
        when(ownerDao.getOwnerById(ownerId)).thenReturn(null);

        // Act
        Owner result = ownerService.getOwnerById(ownerId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveOwner() {
        // Arrange
        Owner owner = new Owner();
        when(ownerDao.saveOwner(owner)).thenReturn(owner);

        // Act
        Owner result = ownerService.saveOwner(owner);

        // Assert
        assertEquals(owner, result);
    }

    @Test
    public void testDeleteOwner() {
        // Arrange
        long ownerId = 1L;

        // Act
        ownerService.deleteOwner(ownerId);

        // Assert
        verify(ownerDao, times(1)).deleteOwner(ownerId);
    }

    @Test
    public void testUpdateOwner() {
        // Arrange
        long ownerId = 1L;
        Owner owner = new Owner();
        Owner updatedOwner = new Owner();
        updatedOwner.setId(ownerId);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
        when(ownerDao.saveOwner(updatedOwner)).thenReturn(updatedOwner);

        // Act
        Owner result = ownerService.updateOwner(ownerId, updatedOwner);

        // Assert
        assertEquals(updatedOwner, result);
    }

    @Test
    public void testGetAllProductsByOwnerId() {
        // Arrange
        long ownerId = 1L;
        Owner owner = new Owner();
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        owner.setProducts(products);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

        // Act
        List<Product> result = ownerService.getAllProductsByOwnerId(ownerId);

        // Assert
        assertEquals(products.size(), result.size());
    }

    @Test
    public void testSaveProductForOwner() {
        // Arrange
        long ownerId = 1L;
        Product product = new Product();
        Owner owner = new Owner();
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
        when(ownerDao.saveProductForOwner(owner, product)).thenReturn(product);

        // Act
        Product result = ownerService.saveProductForOwner(ownerId, product);

        // Assert
        assertEquals(product, result);
    }

    @Test
    public void testAddCarToOwner() {
        // Arrange
        long ownerId = 1L;
        long carId = 1L;
        Owner owner = new Owner();
        Car car = new Car();
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
        when(carDao.getCarById(carId)).thenReturn(car);

        // Act
        ownerService.addCarToOwner(ownerId, carId);

        // Assert
        assertEquals(1, owner.getCars().size());
        assertEquals(car, owner.getCars().get(0));
    }

    @Test
    public void testRemoveCarFromOwner() {
        // Arrange
        long ownerId = 1L;
        long carId = 1L;
        Owner owner = new Owner();
        Car car = new Car();
        car.setId(carId);
        owner.getCars().add(car);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

        // Act
        ownerService.removeCarFromOwner(ownerId, carId);

        // Assert
        assertEquals(0, owner.getCars().size());
    }

    @Test
    public void testUpdateOwnerCars() {
        // Arrange
        long ownerId = 1L;
        List<Long> carIds = List.of(1L, 2L, 3L);
        Owner owner = new Owner();
        List<Car> cars = new ArrayList<>();
        for (Long id : carIds) {
            Car car = new Car();
            car.setId(id);
            cars.add(car);
        }
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
        when(carDao.getCarById(anyLong())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
        });

        // Act
        ownerService.updateOwnerCars(ownerId, carIds);

        // Assert
        assertEquals(cars.size(), owner.getCars().size());
    }

    @Test
    public void testGetAllCarsOfOwner() {
        // Arrange
        long ownerId = 1L;
        Owner owner = new Owner();
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        cars.add(new Car());
        owner.setCars(cars);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

        // Act
        List<Car> result = ownerService.getAllCarsOfOwner(ownerId);

        // Assert
        assertEquals(cars.size(), result.size());
    }
}