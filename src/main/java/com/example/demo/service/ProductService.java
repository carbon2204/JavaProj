
package com.example.demo.service;

import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final CacheService cacheService;
    private final ProductDao productDao;
    private final ProductRepository productRepository;
    private final OwnerDao ownerDao;

    @Autowired
    public ProductService(ProductDao productDao, ProductRepository productRepository, CacheService cacheService, OwnerDao ownerDao) {
        this.productRepository = productRepository;
        this.productDao = productDao;
        this.cacheService = cacheService;
        this.ownerDao=ownerDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    public Product saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productDao.getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            return productDao.saveProduct(existingProduct);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }

    public List<Product> findAllProductsByOwnerId(Long ownerId) {
        String cacheKey = "productsByOwner:" + ownerId;
        if (cacheService.containsKey(cacheKey)) {
            return (List<Product>) cacheService.get(cacheKey);
        } else {
            List<Product> products = productRepository.findAllProductsByOwnerId(ownerId);
            cacheService.put(cacheKey, products);
            return products;
        }
    }

    public Product addProductToOwner(Long ownerId, Long productId) {
        Owner owner = ownerDao.getOwnerById(ownerId);
        Product product = productDao.getProductById(productId);
        if (owner != null && product != null) {
            product.setOwner(owner);
            productDao.saveProduct(product);
            return product;
        }
        return null;
    }

    public void removeProductFromOwner(Long ownerId, Long productId) {
        if(cacheService.containsKey("productsByOwner:" + ownerId)){
            cacheService.remove("productsByOwner:" + ownerId);
        }
        Owner owner = ownerDao.getOwnerById(ownerId);
        if (owner != null) {
            Product productToRemove = owner.getProducts()
                    .stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst()
                    .orElse(null);
            if (productToRemove != null) {
                owner.getProducts().remove(productToRemove);
                ownerDao.saveOwner(owner);
            }
        }
    }

    public Product updateProductForOwner(Long ownerId, Long productId, Product newProduct) {
        if(cacheService.containsKey("productsByOwner:" + ownerId)){
            cacheService.remove("productsByOwner:" + ownerId);
        }
        Owner owner = ownerDao.getOwnerById(ownerId);
        Product productToUpdate = owner.getProducts()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setName(newProduct.getName());
            productToUpdate.setPrice(newProduct.getPrice());
            productDao.saveProduct(productToUpdate);
            return productToUpdate;
        }
        return null;
    }
}
