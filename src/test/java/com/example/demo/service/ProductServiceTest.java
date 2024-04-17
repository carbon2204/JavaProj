package com.example.demo.service;

import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OwnerDao ownerDao;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> products = List.of(new Product(), new Product());
        when(productDao.getAllProducts()).thenReturn(products);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(products.size(), result.size());
    }

    @Test
    public void testGetProductById_Exists() {
        // Arrange
        long productId = 1L;
        Product product = new Product();
        when(productDao.getProductById(productId)).thenReturn(product);

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertEquals(product, result);
    }

    @Test
    public void testGetProductById_NotExists() {
        // Arrange
        long productId = 1L;
        when(productDao.getProductById(productId)).thenReturn(null);

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        Product product = new Product();
        when(productDao.saveProduct(product)).thenReturn(product);

        // Act
        Product result = productService.saveProduct(product);

        // Assert
        assertEquals(product, result);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        long productId = 1L;
        Product existingProduct = new Product();
        Product updatedProduct = new Product();
        when(productDao.getProductById(productId)).thenReturn(existingProduct);
        when(productDao.saveProduct(existingProduct)).thenReturn(updatedProduct);

        // Act
        Product result = productService.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals(updatedProduct, result);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        long productId = 1L;

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productDao, times(1)).deleteProduct(productId);
    }

    @Test
    public void testFindAllProductsByOwnerId() {
        // Arrange
        long ownerId = 1L;
        List<Product> products = List.of(new Product(), new Product());
        when(productRepository.findAllProductsByOwnerId(ownerId)).thenReturn(products);

        // Act
        List<Product> result = productService.findAllProductsByOwnerId(ownerId);

        // Assert
        assertEquals(products, result);
    }

    @Test
    public void testAddProductToOwner() {
        // Arrange
        long ownerId = 1L;
        long productId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        Product product = new Product();
        product.setId(productId);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
        when(productDao.getProductById(productId)).thenReturn(product);
        when(productDao.saveProduct(product)).thenReturn(product);

        // Act
        Product result = productService.addProductToOwner(ownerId, productId);

        // Assert
        assertEquals(product, result);
        assertEquals(owner, product.getOwner());
    }

    @Test
    public void testRemoveProductFromOwner() {
        // Arrange
        long ownerId = 1L;
        long productId = 1L;
        Owner owner = new Owner();
        Product product = new Product();
        product.setId(productId);
        owner.getProducts().add(product);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

        // Act
        productService.removeProductFromOwner(ownerId, productId);

        // Assert
        assertFalse(owner.getProducts().contains(product));
        verify(ownerDao, times(1)).saveOwner(owner);
    }

    @Test
    public void testUpdateProductForOwner() {
        // Arrange
        long ownerId = 1L;
        long productId = 1L;
        Product updatedProduct = new Product();
        Owner owner = new Owner();
        owner.setId(ownerId);
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setPrice(100);
        owner.getProducts().add(existingProduct);
        when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
        when(productDao.getProductById(productId)).thenReturn(existingProduct);
        when(productDao.saveProduct(existingProduct)).thenReturn(existingProduct);

        // Act
        Product result = productService.updateProductForOwner(ownerId, productId, updatedProduct);

        // Assert
        assertEquals(existingProduct, result);
        assertEquals(updatedProduct.getName(), existingProduct.getName());
        assertEquals(updatedProduct.getPrice(), existingProduct.getPrice());
    }
}

