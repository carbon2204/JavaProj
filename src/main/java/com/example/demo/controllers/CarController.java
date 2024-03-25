package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CarService;

import java.util.List;

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

    @PostMapping
    public Car saveCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }



    @PostMapping("/analyze")
    public Car analyzeText(@RequestParam String text) {
        return carService.analyzeText(text);
    }



    @PostMapping("/{carId}/owners/{ownerId}")
    public void addOwnerToCar(@PathVariable Long carId, @PathVariable Long ownerId) {
        carService.addOwnerToCar(carId, ownerId);
    }

    @DeleteMapping("/{carId}/owners/{ownerId}")
    public void removeOwnerFromCar(@PathVariable Long carId, @PathVariable Long ownerId) {
        carService.removeOwnerFromCar(carId, ownerId);
    }

    @PutMapping("/{carId}/owners")
    public void updateCarOwners(@PathVariable Long carId, @RequestBody List<Long> ownerIds) {
        carService.updateCarOwners(carId, ownerIds);
    }

    @GetMapping("/{carId}/owners")
    public List<Owner> getAllOwnersOfCar(@PathVariable Long carId) {
        return carService.getAllOwnersOfCar(carId);
    }

    @PostMapping("/{carId}/products/{productId}")
    public void addProductToCar(@PathVariable Long carId, @PathVariable Long productId) {
        carService.addProductToCar(carId, productId);
    }

    @DeleteMapping("/{carId}/products/{productId}")
    public void removeProductFromCar(@PathVariable Long carId, @PathVariable Long productId) {
        carService.removeProductFromCar(carId, productId);
    }

    @PutMapping("/{carId}/products")
    public void updateCarProducts(@PathVariable Long carId, @RequestBody List<Long> productIds) {
        carService.updateCarProducts(carId, productIds);
    }

    @GetMapping("/{carId}/products")
    public List<Product> getAllProductsOfCar(@PathVariable Long carId) {
        return carService.getAllProductsOfCar(carId);
    }

}

