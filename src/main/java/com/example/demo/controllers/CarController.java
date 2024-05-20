package com.example.demo.controllers;

import com.example.demo.model.Car;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.service.CarService;
import com.example.demo.service.OwnerService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * The type Car controller.
 */
@Controller
@RequestMapping("/")
public class CarController {

  private final CarService carService;
  private final OwnerService ownerService;
  @Autowired
    public CarController(CarService carService, OwnerService ownerService) {
    this.carService = carService;
    this.ownerService = ownerService;
  }

  /*@GetMapping("/cars")
    public List<Car> getAllCars() {
    return carService.getAllCars();
  }*/

  @GetMapping("/cars")
  public String getAllCars(Model model) {
    List<Car> cars = carService.getAllCars();
    model.addAttribute("cars", cars);
    return "car-list"; // Вернуть имя представления (HTML-шаблона)
  }

  /*@GetMapping("/cars/{id}")
    public Car getCarById(@PathVariable Long id) {
    return carService.getCarById(id);
  }*/
  @GetMapping("/cars/{id}")
  public String getCarById(@PathVariable Long id, Model model) {
    Car car = carService.getCarById(id);
    model.addAttribute("car", car);
    return "car-details"; // Вернуть имя представления (HTML-шаблона)
  }

/*  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PostMapping
    public Car saveCar(@RequestBody Car car) {
    return carService.saveCar(car);
  }*/

  /*@Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/cars/update/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
    return carService.updateCar(id, car);
  }*/

  /*@PostMapping("/cars/update/{id}")
  public String updateCar(@PathVariable Long id, @ModelAttribute Car car) {
    carService.updateCar(id, car);
    return "redirect:/cars";
  }*/
  @GetMapping("/cars/edit/{id}")
  public String editCarById(@PathVariable Long id, Model model) {
    Car car = carService.getCarById(id);
    model.addAttribute("car", car);
    return "edit-car"; // Название шаблона страницы редактирования автомобиля
  }
  @PostMapping("/cars/update/{id}")
  public String updateCar(@PathVariable Long id, @RequestParam String vin, @RequestParam String make,
                          @RequestParam String model, @RequestParam String year) {
    Car existingCar = carService.getCarById(id);
    if (existingCar != null) {
      existingCar.setVin(vin.isEmpty() ? "-" : vin);
      existingCar.setMake(make.isEmpty() ? "-" : make);
      existingCar.setModel(model.isEmpty() ? "-" : model);
      existingCar.setYear(year.isEmpty() ? "-" : year);  // assuming 0 represents empty year
      carService.updateCar(id, existingCar);
    }
    return "redirect:/cars"; // Возвращаемся на главную страницу после обновления
  }

  /*@Transactional
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/cars/{id}")
    public Long deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return id;
  }*/

  @PostMapping("/cars/delete/{id}")
  public String deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return "redirect:/cars";
  }

  /*@Transactional
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/cars/create")
    public Car createText(@RequestParam String text) {
    return carService.analyzeText(text);
  }*/

  /*@PostMapping("/cars/create")
  public String createText(@RequestParam String text) {
    carService.analyzeText(text);
    return "redirect:/cars";
  }*/

  @PostMapping("/cars/create")
  public String createText(@RequestParam String text) {
    if (text.trim().isEmpty()) {
      // Пустое значение текста, возвращаем страницу с сообщением об ошибке
      return "redirect:/cars?error=empty";
    }
    // Регулярные выражения для поиска VIN, марки, модели и года автомобиля
    String vinRegex = "VIN:\\s*([A-Za-z0-9]+)";
    String makeRegex = "Бренд:\\s*([A-Za-z0-9]+)";
    String modelRegex = "Модель:\\s*([A-Za-z0-9\\s]+)";
    final String yearRegex = "Год:\\s*(\\d{4})";


    carService.analyzeText(text);
    return "redirect:/cars";
  }

  /*@Transactional
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/cars/{carId}/add/owners/{ownerId}")
    public void addOwnerToCar(@PathVariable Long carId, @PathVariable Long ownerId) {
    carService.addOwnerToCar(carId, ownerId);
  }*/
  @GetMapping("/cars/{carId}/add/owners")
  public String showAddOwnersForm(@PathVariable Long carId, Model model) {
    List<Owner> owners = ownerService.getAllOwners();
    Car car = carService.getCarById(carId);
    model.addAttribute("owners", owners);
    model.addAttribute("car", car);
    return "add-owner";
  }

  @PostMapping("/cars/{carId}/add/owners/{ownerId}")
  public String addOwnerToCar(@PathVariable Long carId, @PathVariable Long ownerId, RedirectAttributes redirectAttributes) {
    carService.addOwnerToCar(carId, ownerId);
    redirectAttributes.addFlashAttribute("message", "Owner added successfully");
    return "redirect:/cars/" + carId;
  }

  /*@Transactional
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/cars/{carId}/remove/owners/{ownerId}")
    public void removeOwnerFromCar(@PathVariable Long carId, @PathVariable Long ownerId) {
    carService.removeOwnerFromCar(carId, ownerId);
  }*/

  @Transactional
  @PostMapping("/cars/{carId}/remove/owners/{ownerId}")
  public String removeOwnerFromCar(@PathVariable Long carId, @PathVariable Long ownerId, RedirectAttributes redirectAttributes) {
    carService.removeOwnerFromCar(carId, ownerId);
    redirectAttributes.addFlashAttribute("message", "Owner removed successfully");
    return "redirect:/cars/" + carId;
  }

  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/cars/{carId}/owners")
    public void updateCarOwners(@PathVariable Long carId, @RequestBody List<Long> ownerIds) {
    carService.updateCarOwners(carId, ownerIds);
  }

  @GetMapping("/cars/{carId}/owners")
    public List<Owner> getAllOwnersOfCar(@PathVariable Long carId) {
    return carService.getAllOwnersOfCar(carId);
  }

  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/cars/{carId}/products/{productId}")
    public void addProductToCar(@PathVariable Long carId, @PathVariable Long productId) {
    carService.addProductToCar(carId, productId);
  }

  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/cars/{carId}/products/{productId}")
    public void removeProductFromCar(@PathVariable Long carId, @PathVariable Long productId) {
    carService.removeProductFromCar(carId, productId);
  }

  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/cars/{carId}/products")
    public void updateCarProducts(@PathVariable Long carId, @RequestBody List<Long> productIds) {
    carService.updateCarProducts(carId, productIds);
  }

  @Transactional
  @GetMapping("/cars/{carId}/products")
    public List<Product> getAllProductsOfCar(@PathVariable Long carId) {
    return carService.getAllProductsOfCar(carId);
  }
  @GetMapping("/owners/home")
  public String getOwnersPage() {
    return "ownerList"; // Название шаблона страницы с базой данных владельцев
  }

}

