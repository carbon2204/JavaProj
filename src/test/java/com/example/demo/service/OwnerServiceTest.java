package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

  @Mock
  private OwnerDao ownerDao;

  @Mock
  private CarDao carDao;

  @InjectMocks
  private OwnerService ownerService;

  @Test
  void testGetAllOwners() {
    List<Owner> owners = new ArrayList<>();
    owners.add(new Owner());
    owners.add(new Owner());
    when(ownerDao.getAllOwners()).thenReturn(owners);

    List<Owner> result = ownerService.getAllOwners();

    assertEquals(owners.size(), result.size());
  }

  @Test
  void testGetOwnerById_Exists() {
    long ownerId = 1L;
    Owner owner = new Owner();
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

    Owner result = ownerService.getOwnerById(ownerId);

    assertEquals(owner, result);
  }

  @Test
  void testGetOwnerById_NotExists() {
    long ownerId = 1L;
    when(ownerDao.getOwnerById(ownerId)).thenReturn(null);

    Owner result = ownerService.getOwnerById(ownerId);

    assertNull(result);
  }

  @Test
  void testSaveOwner() {
    Owner owner = new Owner();
    when(ownerDao.saveOwner(owner)).thenReturn(owner);

    Owner result = ownerService.saveOwner(owner);

    assertEquals(owner, result);
  }

  @Test
  void testDeleteOwner() {
    long ownerId = 1L;

    ownerService.deleteOwner(ownerId);

    verify(ownerDao, times(1)).deleteOwner(ownerId);
  }

  @Test
  void testUpdateOwner() {
    long ownerId = 1L;

    // Используем один и тот же объект для подготовки мока и вызова метода
    Owner owner = new Owner();
    owner.setId(ownerId);

    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
    when(ownerDao.saveOwner(owner)).thenReturn(owner);

    Owner result = ownerService.updateOwner(ownerId, owner);

    assertEquals(owner, result);
  }

  @Test
  void testGetAllProductsByOwnerId() {
    final long ownerId = 1L;
    Owner owner = new Owner();
    List<Product> products = new ArrayList<>();
    products.add(new Product());
    products.add(new Product());
    owner.setProducts(products);
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

    List<Product> result = ownerService.getAllProductsByOwnerId(ownerId);

    assertEquals(products.size(), result.size());
  }

  @Test
  void testSaveProductForOwner() {
    long ownerId = 1L;
    Product product = new Product();
    Owner owner = new Owner();
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
    when(ownerDao.saveProductForOwner(owner, product)).thenReturn(product);

    Product result = ownerService.saveProductForOwner(ownerId, product);

    assertEquals(product, result);
  }

  @Test
  void testAddCarToOwner() {
    long ownerId = 1L;
    final long carId = 1L;
    Owner owner = new Owner();
    Car car = new Car();

    // Убедимся, что оба списка (у владельца и у машины) инициализированы
    owner.setCars(new ArrayList<>());
    car.setOwners(new ArrayList<>());

    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
    when(carDao.getCarById(carId)).thenReturn(car);

    ownerService.addCarToOwner(ownerId, carId);

    // Проверяем, что машина добавлена к владельцу, а владелец добавлен к машине
    assertEquals(1, owner.getCars().size());
    assertEquals(car, owner.getCars().get(0));

    assertEquals(1, car.getOwners().size());
    assertEquals(owner, car.getOwners().get(0));
  }

  @Test
  void testRemoveCarFromOwner() {
    final long ownerId = 1L;
    long carId = 1L;
    Owner owner = new Owner();
    Car car = new Car();
    car.setId(carId);

    // Убедимся, что список машин инициализирован, и добавим машину для удаления
    owner.setCars(new ArrayList<>());
    owner.getCars().add(car);

    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

    ownerService.removeCarFromOwner(ownerId, carId);

    assertEquals(0, owner.getCars().size());
  }

  @Test
  void testUpdateOwnerCars() {
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

    ownerService.updateOwnerCars(ownerId, carIds);

    assertEquals(cars.size(), owner.getCars().size());
  }

  @Test
  void testGetAllCarsOfOwner() {
    final long ownerId = 1L;
    Owner owner = new Owner();
    List<Car> cars = new ArrayList<>();
    cars.add(new Car());
    cars.add(new Car());
    owner.setCars(cars);
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

    List<Car> result = ownerService.getAllCarsOfOwner(ownerId);

    assertEquals(cars.size(), result.size());
  }
}