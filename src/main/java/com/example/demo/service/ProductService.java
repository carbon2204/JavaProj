
package com.example.demo.service;

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
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
}
