package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.service.OwnerService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Owner controller.
 */
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

  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  @PostMapping("/create")
    public Owner createOwner(@RequestBody Owner owner) {
    return ownerService.saveOwner(owner);
  }

  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
    return ownerService.updateOwner(id, owner);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
    public Long deleteOwner(@PathVariable Long id) {
    ownerService.deleteOwner(id);
    return id;
  }

  @GetMapping("/{ownerId}/products")
    public List<Product> getAllProductsByOwnerId(@PathVariable Long ownerId) {
    return ownerService.getAllProductsByOwnerId(ownerId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/{ownerId}/products")
    public Product saveProductForOwner(@PathVariable Long ownerId, @RequestBody Product product) {
    return ownerService.saveProductForOwner(ownerId, product);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/{ownerId}/cars/{carId}")
    public void addCarToOwner(@PathVariable Long ownerId, @PathVariable Long carId) {
    ownerService.addCarToOwner(ownerId, carId);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{ownerId}/cars/{carId}")
    public void removeCarFromOwner(@PathVariable Long ownerId, @PathVariable Long carId) {
    ownerService.removeCarFromOwner(ownerId, carId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{ownerId}/cars")
    public void updateOwnerCars(@PathVariable Long ownerId, @RequestBody List<Long> carIds) {
    ownerService.updateOwnerCars(ownerId, carIds);
  }

  @GetMapping("/{ownerId}/cars")
    public List<Car> getAllCarsOfOwner(@PathVariable Long ownerId) {
    return ownerService.getAllCarsOfOwner(ownerId);
  }

  /**
   * Add several owners list.
   *
   * @param owners the owners
   * @return the list
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/addSeveralOwners")
  public List<Owner> addSeveralOwners(@RequestBody List<Owner> owners) {
    return owners.stream()
            .map(ownerService::saveOwner)
            .toList();
  }
}



