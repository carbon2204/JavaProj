package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.service.CarService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Car controller.
 */
@RestController
@RequestMapping("/cars")
public class CarController {

  private final CarService carService;

  @Autowired
    public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping
    public List<Car> getAllCars() {
    return carService.getAllCars();
  }

  @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
    return carService.getCarById(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping
    public Car saveCar(@RequestBody Car car) {
    return carService.saveCar(car);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/update/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
    return carService.updateCar(id, car);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
    public Long deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return id;
  }


  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
    public Car createText(@RequestParam String text) {
    return carService.analyzeText(text);
  }


  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/{carId}/owners/{ownerId}")
    public void addOwnerToCar(@PathVariable Long carId, @PathVariable Long ownerId) {
    carService.addOwnerToCar(carId, ownerId);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{carId}/owners/{ownerId}")
    public void removeOwnerFromCar(@PathVariable Long carId, @PathVariable Long ownerId) {
    carService.removeOwnerFromCar(carId, ownerId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{carId}/owners")
    public void updateCarOwners(@PathVariable Long carId, @RequestBody List<Long> ownerIds) {
    carService.updateCarOwners(carId, ownerIds);
  }

  @GetMapping("/{carId}/owners")
    public List<Owner> getAllOwnersOfCar(@PathVariable Long carId) {
    return carService.getAllOwnersOfCar(carId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/{carId}/products/{productId}")
    public void addProductToCar(@PathVariable Long carId, @PathVariable Long productId) {
    carService.addProductToCar(carId, productId);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{carId}/products/{productId}")
    public void removeProductFromCar(@PathVariable Long carId, @PathVariable Long productId) {
    carService.removeProductFromCar(carId, productId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{carId}/products")
    public void updateCarProducts(@PathVariable Long carId, @RequestBody List<Long> productIds) {
    carService.updateCarProducts(carId, productIds);
  }

  @GetMapping("/{carId}/products")
    public List<Product> getAllProductsOfCar(@PathVariable Long carId) {
    return carService.getAllProductsOfCar(carId);
  }

}

