package com.example.demo.repository;

import com.example.demo.model.Car;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT p FROM Car c JOIN c.products p WHERE c.id = :carId")
    List<Product> findAllProductsByCarId(Long carId);
}
