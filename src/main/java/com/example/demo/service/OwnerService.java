package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import java.util.LinkedList;
import java.util.List;

import com.example.demo.repository.CarRepository;
import com.example.demo.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Owner service.
 */
@Service
public class OwnerService {
  private final OwnerDao ownerDao;
  private final CarDao carDao;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
    public OwnerService(OwnerDao ownerDao, CarDao carDao, OwnerRepository ownerRepository, CarRepository carRepository) {
    this.carDao = carDao;
    this.ownerDao = ownerDao;
    this.ownerRepository = ownerRepository;
    this.carRepository = carRepository;
  }

  public List<Owner> getAllOwners() {
    return ownerDao.getAllOwners();
  }

  public Owner getOwnerById(Long id) {
    return ownerDao.getOwnerById(id);
  }

  public Owner saveOwner(Owner owner) {
    return ownerDao.saveOwner(owner);
  }

  public void deleteOwner(Long id) {
    ownerDao.deleteOwner(id);
  }

  /**
     * Update owner owner.
     *
     * @param id    the id
     * @param owner the owner
     * @return the owner
     */
  public Owner updateOwner(Long id, Owner owner) {
    Owner existingOwner = ownerDao.getOwnerById(id);
    if (existingOwner != null) {
      existingOwner.setName(owner.getName());
      return ownerDao.saveOwner(existingOwner);
    }
    return null;
  }

  /**
     * Gets all products by owner id.
     *
     * @param ownerId the owner id
     * @return the all products by owner id
     */
  public List<Product> getAllProductsByOwnerId(Long ownerId) {
    Owner owner = ownerDao.getOwnerById(ownerId);
    if (owner != null) {
      return owner.getProducts();
    }
    return new LinkedList<>();
  }

  /**
     * Save product for owner product.
     *
     * @param ownerId the owner id
     * @param product the product
     * @return the product
     */
  public Product saveProductForOwner(Long ownerId, Product product) {
    Owner owner = ownerDao.getOwnerById(ownerId);
    if (owner != null) {
      product.setOwner(owner);
      return ownerDao.saveProductForOwner(owner, product);
    }
    return null;
  }

  /**
     * Add car to owner.
     *
     * @param ownerId the owner id
     * @param carId   the car id
     */
  @Transactional
  public void addCarToOwner(Long ownerId, Long carId) {
    Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Invalid owner Id:" + ownerId));
    Car car = carRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));

    owner.getCars().add(car);
    car.getOwners().add(owner);

    ownerRepository.save(owner);
    carRepository.save(car);
  }

  /**
     * Remove car from owner.
     *
     * @param ownerId the owner id
     * @param carId   the car id
     */
  @Transactional
  public void removeCarFromOwner(Long ownerId, Long carId) {
    Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Invalid owner Id:" + ownerId));
    Car car = carRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));

    owner.getCars().remove(car);
    car.getOwners().remove(owner);

    ownerRepository.save(owner);
    carRepository.save(car);
  }

  /**
     * Update owner cars.
     *
     * @param ownerId the owner id
     * @param carIds  the car ids
     */
  public void updateOwnerCars(Long ownerId, List<Long> carIds) {
    Owner owner = ownerDao.getOwnerById(ownerId);
    if (owner != null) {
      List<Car> cars = carIds.stream()
              .map(carDao::getCarById).toList();
      owner.setCars(cars);
      ownerDao.saveOwner(owner);
    }
  }

  /**q
     * Gets all cars of owner.
     *
     * @param ownerId the owner id
     * @return the all cars of owner
     */
  public List<Car> getAllCarsOfOwner(Long ownerId) {
    Owner owner = ownerDao.getOwnerById(ownerId);
    if (owner != null) {
      return owner.getCars();
    }
    return new LinkedList<>();
  }

  public List<Owner> addSeveralOwners(List<Owner> owners){
    return owners.stream()
            .map(ownerDao::saveOwner).toList();
  }
}