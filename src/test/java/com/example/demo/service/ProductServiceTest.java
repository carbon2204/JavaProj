package com.example.demo.service;

import com.example.demo.dao.OwnerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Owner;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
 class ProductServiceTest {

  @Mock
  private ProductDao productDao;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private OwnerDao ownerDao;

  @Mock
  private CacheService cacheService;

  @InjectMocks
  private ProductService productService;

  @Test
  void testGetAllProducts() {
    List<Product> products = List.of(new Product(), new Product());
    when(productDao.getAllProducts()).thenReturn(products);

    List<Product> result = productService.getAllProducts();

    assertEquals(products.size(), result.size());
  }

  @Test
  void testGetProductById_Exists() {
    long productId = 1L;
    Product product = new Product();
    when(productDao.getProductById(productId)).thenReturn(product);

    Product result = productService.getProductById(productId);

    assertEquals(product, result);
  }

  @Test
  void testGetProductById_NotExists() {
    long productId = 1L;
    when(productDao.getProductById(productId)).thenReturn(null);

    Product result = productService.getProductById(productId);

    assertNull(result);
  }

  @Test
  void testSaveProduct() {
    Product product = new Product();
    when(productDao.saveProduct(product)).thenReturn(product);

    Product result = productService.saveProduct(product);

    assertEquals(product, result);
  }

  @Test
  void testUpdateProduct() {
    long productId = 1L;
    Product existingProduct = new Product();
    Product updatedProduct = new Product();
    when(productDao.getProductById(productId)).thenReturn(existingProduct);
    when(productDao.saveProduct(existingProduct)).thenReturn(updatedProduct);

    Product result = productService.updateProduct(productId, updatedProduct);

    assertEquals(updatedProduct, result);
  }

  @Test
  void testDeleteProduct() {
    long productId = 1L;

    productService.deleteProduct(productId);

    verify(productDao, times(1)).deleteProduct(productId);
  }

  @Test
  void testFindAllProductsByOwnerId() {
    long ownerId = 1L;
    List<Product> products = List.of(new Product(), new Product());
    when(cacheService.containsKey("productsByOwner:"
            + ownerId)).thenReturn(false);

    when(productRepository.findAllProductsByOwnerId(ownerId)).thenReturn(products);

    List<Product> result = productService.findAllProductsByOwnerId(ownerId);

    assertEquals(products, result);
  }

  @Test
  void testAddProductToOwner() {
    long ownerId = 1L;
    long productId = 1L;
    Owner owner = new Owner();
    owner.setId(ownerId);
    Product product = new Product();
    product.setId(productId);
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);
    when(productDao.getProductById(productId)).thenReturn(product);
    when(productDao.saveProduct(product)).thenReturn(product);

    Product result = productService.addProductToOwner(ownerId, productId);

    assertEquals(product, result);
    assertEquals(owner, product.getOwner());
  }

  @Test
  void testRemoveProductFromOwner() {
    final long ownerId = 1L;
    long productId = 1L;
    Owner owner = new Owner();
    owner.setProducts(new ArrayList<>());
    Product product = new Product();
    product.setId(productId);
    owner.getProducts().add(product);
    when(ownerDao.getOwnerById(ownerId)).thenReturn(owner);

    productService.removeProductFromOwner(ownerId, productId);

    assertFalse(owner.getProducts().contains(product));
    verify(ownerDao, times(1)).saveOwner(owner);
  }
}

