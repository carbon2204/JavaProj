package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private final OwnerDao ownerDao;
    private final CarDao carDao;
    @Autowired
    public OwnerService(OwnerDao ownerDao, CarDao carDao) {
        this.carDao = carDao;
        this.ownerDao = ownerDao;
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

    public Owner updateOwner(Long id, Owner owner) {
        Owner existingOwner = ownerDao.getOwnerById(id);
        if (existingOwner != null) {
            existingOwner.setName(owner.getName());
            return ownerDao.saveOwner(existingOwner);
        }
        return null;
    }

    public List<Product> getAllProductsByOwnerId(Long ownerId) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (owner != null) {
            return owner.getProducts();
        }
        return null;
    }

    public Product saveProductForOwner(Long ownerId, Product product) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (owner != null) {
            product.setOwner(owner);
            return ownerDao.saveProductForOwner(owner, product);
        }
        return null;
    }




    public void addCarToOwner(Long ownerId, Long carId) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        Car car = carDao.getCarById(carId);
        if (owner != null && car != null) {
            owner.getCars().add(car);  // Добавление машины к владельцу
            car.getOwners().add(owner);  // Добавление владельца к машине
            ownerDao.saveOwner(owner);
            carDao.saveCar(car);
        }
    }

    public void removeCarFromOwner(Long ownerId, Long carId) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (owner != null) {
            owner.getCars().removeIf(car -> car.getId().equals(carId));
            ownerDao.saveOwner(owner);
        }
    }

    public void updateOwnerCars(Long ownerId, List<Long> carIds) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (owner != null) {
            List<Car> cars = carIds.stream()
                    .map(carId -> carDao.getCarById(carId)).toList();
            owner.setCars(cars);
            ownerDao.saveOwner(owner);
        }
    }

    public List<Car> getAllCarsOfOwner(Long ownerId) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (owner != null) {
            return owner.getCars();
        }
        return new LinkedList<Car>();
    }
}