package com.example.demo.dao;

import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.demo.repository.OwnerRepository;

import java.util.List;

@Repository
public class OwnerDao {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerDao(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Product saveProductForOwner(Owner owner, Product product) {
        owner.getProducts().add(product);
        product.setOwner(owner);
        return product;
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}