package com.example.demo.dao;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The type Car dao.
 */
@Repository
public class CarDao {

  private final CarRepository carRepository;

  @Autowired
    public CarDao(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  public Car getCarById(Long id) {
    return carRepository.findById(id).orElse(null);
  }

  public Car saveCar(Car car) {
    return carRepository.save(car);
  }

  public void deleteCar(Long id) {
    carRepository.deleteById(id);
  }
}