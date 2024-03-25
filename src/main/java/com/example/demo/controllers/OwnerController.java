package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    @PostMapping
    public Owner saveOwner(@RequestBody Owner owner) {
        return ownerService.saveOwner(owner);
    }

    @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        return ownerService.updateOwner(id, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }

    @GetMapping("/{ownerId}/products")
    public List<Product> getAllProductsByOwnerId(@PathVariable Long ownerId) {
        return ownerService.getAllProductsByOwnerId(ownerId);
    }

    @PostMapping("/{ownerId}/products")
    public Product saveProductForOwner(@PathVariable Long ownerId, @RequestBody Product product) {
        return ownerService.saveProductForOwner(ownerId, product);
    }





    @PostMapping("/{ownerId}/cars/{carId}")
    public void addCarToOwner(@PathVariable Long ownerId, @PathVariable Long carId) {
        ownerService.addCarToOwner(ownerId, carId);
    }

    @DeleteMapping("/{ownerId}/cars/{carId}")
    public void removeCarFromOwner(@PathVariable Long ownerId, @PathVariable Long carId) {
        ownerService.removeCarFromOwner(ownerId, carId);
    }

    @PutMapping("/{ownerId}/cars")
    public void updateOwnerCars(@PathVariable Long ownerId, @RequestBody List<Long> carIds) {
        ownerService.updateOwnerCars(ownerId, carIds);
    }

    @GetMapping("/{ownerId}/cars")
    public List<Car> getAllCarsOfOwner(@PathVariable Long ownerId) {
        return ownerService.getAllCarsOfOwner(ownerId);
    }
}


