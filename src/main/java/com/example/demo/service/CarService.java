package com.example.demo.service;

import com.example.demo.dao.CarDao;
import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Service
public class CarService {

    private final CarDao carDao;
    private final ProductDao productDao;
    private final OwnerDao ownerDao;
    private final CacheService cacheService;
    private CarRepository carRepository;

    @Autowired
    public CarService(CarDao carDao, CarRepository carRepository, CacheService cacheService, ProductDao productDao, OwnerDao ownerDao) {
        this.carDao = carDao;
        this.carRepository = carRepository;
        this.cacheService = cacheService;
        this.productDao = productDao;
        this.ownerDao = ownerDao;
    }

    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    public Car getCarById(Long id) {
        return carDao.getCarById(id);
    }

    public Car saveCar(Car car) {
        return carDao.saveCar(car);
    }

    public Car updateCar(Long id, Car car) {
        Car existingCar = carDao.getCarById(id);
        if (existingCar != null) {
            existingCar.setVin(car.getVin());
            existingCar.setMake(car.getMake());
            existingCar.setModel(car.getModel());
            existingCar.setYear(car.getYear());
            return carDao.saveCar(existingCar);
        }
        return null;
    }

    public void deleteCar(Long id) {
        carDao.deleteCar(id);
    }


    public Car analyzeText(String text) {
        Car car = new Car();

        // Регулярные выражения для поиска VIN, марки, модели и года автомобиля
        String vinRegex = "VIN:\\s*([A-Za-z0-9]+)";
        String makeRegex = "Бренд:\\s*([A-Za-z0-9]+)";
        String modelRegex = "Модель:\\s*([A-Za-z0-9\\s]+)";
        String yearRegex = "Год:\\s*(\\d{4})";

        // Поиск VIN
        Pattern vinPattern = Pattern.compile(vinRegex);
        Matcher vinMatcher = vinPattern.matcher(text);
        if (vinMatcher.find()) {
            car.setVin(vinMatcher.group(1));
        }

        // Поиск марки
        Pattern makePattern = Pattern.compile(makeRegex);
        Matcher makeMatcher = makePattern.matcher(text);
        if (makeMatcher.find()) {
            car.setMake(makeMatcher.group(1));
        }

        // Поиск модели
        Pattern modelPattern = Pattern.compile(modelRegex);
        Matcher modelMatcher = modelPattern.matcher(text);
        if (modelMatcher.find()) {
            car.setModel(modelMatcher.group(1));
        }

        // Поиск года
        Pattern yearPattern = Pattern.compile(yearRegex);
        Matcher yearMatcher = yearPattern.matcher(text);
        if (yearMatcher.find()) {
            car.setYear(Integer.parseInt(yearMatcher.group(1)));
        }

        return carRepository.save(car);
    }






    public void addOwnerToCar(Long carId, Long ownerId) {
        Car car = carDao.getCarById(carId);
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (car != null && owner != null) {
            car.getOwners().add(owner);  // Добавление владельца к машине
            owner.getCars().add(car);  // Добавление машины к владельцу
            carDao.saveCar(car);
            ownerDao.saveOwner(owner);
        }
    }

    public void removeOwnerFromCar(Long carId, Long ownerId) {
        Car car = carDao.getCarById(carId);
        if (car != null) {
            car.getOwners().removeIf(owner -> owner.getId().equals(ownerId));
            carDao.saveCar(car);
        }
    }

    public void updateCarOwners(Long carId, List<Long> ownerIds) {
        Car car = carDao.getCarById(carId);
        if (car != null) {
            List<Owner> owners = ownerIds.stream()
                    .map(ownerId -> ownerDao.getOwnerById(ownerId)).toList();
            car.setOwners(owners);
            carDao.saveCar(car);
        }
    }

    public List<Owner> getAllOwnersOfCar(Long carId) {
        Car car = carDao.getCarById(carId);
        if (car != null) {
            return car.getOwners();
        }
        return new LinkedList<Owner>();
    }

    public void addProductToCar(Long carId, Long productId) {
        Car car = carDao.getCarById(carId);
        Product product = productDao.getProductById(productId);
        if (car != null && product != null) {
            product.setCar(car);
            car.getProducts().add(product);
            carDao.saveCar(car);
        }
    }

    public void removeProductFromCar(Long carId, Long productId) {
        Car car = carDao.getCarById(carId);
        if (car != null) {
            car.getProducts().removeIf(product -> product.getId().equals(productId));
            carDao.saveCar(car);
        }
    }

    public void updateCarProducts(Long carId, List<Long> productIds) {
        Car car = carDao.getCarById(carId);
        if (car != null) {
            List<Product> products = productIds.stream()
                    .map(productId -> productDao.getProductById(productId)).toList();
            car.setProducts(products);
            carDao.saveCar(car);
        }
    }

    public List<Product> getAllProductsOfCar(Long carId) {
        Car car = carDao.getCarById(carId);
        if (car != null) {
            return car.getProducts();
        }
        return new LinkedList<Product>();
    }
}


