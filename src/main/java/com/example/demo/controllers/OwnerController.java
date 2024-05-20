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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The type Owner controller.
 */
@Controller
@RequestMapping("/owners")
public class OwnerController {

  @Autowired
  private OwnerService ownerService;

  @Autowired
  private CarService carService;

  @Autowired
    public OwnerController(OwnerService ownerService, CarService carService) {
    this.ownerService = ownerService;
    this.carService = carService;
  }

  /*@GetMapping
    public List<Owner> getAllOwners() {
    return ownerService.getAllOwners();
  }*/

  @GetMapping
  public String getAllOwners(Model model) {
    List<Owner> owners = ownerService.getAllOwners();
    model.addAttribute("owners", owners);
    return "owner-list"; // Название шаблона страницы списка владельцев
  }

  /*@GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
    return ownerService.getOwnerById(id);
  }*/

  @GetMapping("/edit/{id}")
  public String getOwnerById(@PathVariable Long id, Model model) {
    Owner owner = ownerService.getOwnerById(id);
    model.addAttribute("owner", owner);
    return "edit-owner"; // Название шаблона страницы редактирования владельца
  }

  /*@ResponseStatus(HttpStatus.CREATED)
  @Transactional
  @PostMapping("/create")
    public Owner createOwner(@RequestBody Owner owner) {
    return ownerService.saveOwner(owner);
  }*/

  @GetMapping("/createform")
  public String createOwnerForm(Model model) {
    model.addAttribute("owner", new Owner());
    return "create-owner"; // Название шаблона страницы создания владельца
  }

  @PostMapping("/create")
  public String createOwner(@RequestParam String name) {
    Owner owner = new Owner();
    owner.setName(name.isEmpty() ? null : name);
    Owner savedOwner = ownerService.saveOwner(owner);
    if (savedOwner.getName() == null) {
      savedOwner.setName("Incognito-" + savedOwner.getId());
      ownerService.saveOwner(savedOwner);
    }
    return "redirect:/owners";
  }

  /*@Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
    return ownerService.updateOwner(id, owner);
  }*/

  @PostMapping("/update/{id}")
  public String updateOwner(@PathVariable Long id, @RequestParam String name) {
    Owner existingOwner = ownerService.getOwnerById(id);
    if (existingOwner != null) {
      existingOwner.setName(name.isEmpty() ? "Incognito-"+existingOwner.getId() : name);
      ownerService.updateOwner(id, existingOwner);
    }
    return "redirect:/owners"; // Возвращаемся на страницу списка владельцев после обновления
  }

  /*@ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
    public Long deleteOwner(@PathVariable Long id) {
    ownerService.deleteOwner(id);
    return id;
  }*/

  @PostMapping("/delete/{id}")
  public String deleteOwner(@PathVariable Long id) {
    ownerService.deleteOwner(id);
    return "redirect:/owners"; // Возвращаемся на страницу списка владельцев после удаления
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

  /*@ResponseStatus(HttpStatus.OK)
  @PostMapping("/{ownerId}/add/{carId}")
    public void addCarToOwner(@PathVariable Long ownerId, @PathVariable Long carId) {
    ownerService.addCarToOwner(ownerId, carId);
  }*/

  @GetMapping("/{ownerId}")
  public String getOwnerDetails(@PathVariable Long ownerId, Model model) {
    Owner owner = ownerService.getOwnerById(ownerId);
    model.addAttribute("owner", owner);
    return "view-owner";
  }

  @PostMapping("/{ownerId}/add/{carId}")
  public String addCarToOwner(@PathVariable Long ownerId, @PathVariable Long carId, RedirectAttributes redirectAttributes) {
    ownerService.addCarToOwner(ownerId, carId);
    redirectAttributes.addFlashAttribute("message", "Car added successfully");
    return "redirect:/owners/" + ownerId;
  }

  /*@ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{ownerId}/delete/{carId}")
    public void removeCarFromOwner(@PathVariable Long ownerId, @PathVariable Long carId) {
    ownerService.removeCarFromOwner(ownerId, carId);
  }*/

  @PostMapping("/{ownerId}/delete/{carId}")
  public String removeCarFromOwner(@PathVariable Long ownerId, @PathVariable Long carId, RedirectAttributes redirectAttributes) {
    ownerService.removeCarFromOwner(ownerId, carId);
    redirectAttributes.addFlashAttribute("message", "Car removed successfully");
    return "redirect:/owners/" + ownerId;
  }

  @GetMapping("/{ownerId}/add/cars")
  public String showAddCarsForm(@PathVariable Long ownerId, Model model) {
    List<Car> cars = carService.getAllCars();
    Owner owner = ownerService.getOwnerById(ownerId);
    model.addAttribute("cars", cars);
    model.addAttribute("owner", owner);
    return "add-car";
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{ownerId}/cars")
    public void updateOwnerCars(@PathVariable Long ownerId, @RequestBody List<Long> carIds) {
    ownerService.updateOwnerCars(ownerId, carIds);
  }

  /*@GetMapping("/{ownerId}/cars")
    public List<Car> getAllCarsOfOwner(@PathVariable Long ownerId) {
    return ownerService.getAllCarsOfOwner(ownerId);
  }*/

  @GetMapping("/view/{id}")
  public String viewOwner(@PathVariable Long id, Model model) {
    Owner owner = ownerService.getOwnerById(id);
    List<Car> cars = ownerService.getAllCarsOfOwner(id);
    owner.setCars(cars);
    model.addAttribute("owner", owner);
    return "view-owner";
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
    return ownerService.addSeveralOwners(owners);
  }
}



