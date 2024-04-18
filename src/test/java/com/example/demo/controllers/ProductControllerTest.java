package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
 class ProductControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Test
  void testGetAllProducts() throws Exception {
    List<Product> products = Arrays.asList(new Product(), new Product());
    when(productService.getAllProducts()).thenReturn(products);

    mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testGetProductById() throws Exception {
    long productId = 1L;
    Product product = new Product();
    when(productService.getProductById(productId)).thenReturn(product);

    mockMvc.perform(get("/products/{id}", productId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(productId)));
  }

  @Test
  void testCreateProduct() throws Exception {
    Product product = new Product();
    when(productService.saveProduct(any())).thenReturn(product);

    mockMvc.perform(post("/products/create")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content("{ \"name\": \"Product A\" }"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("Product A")));
  }

  @Test
  void testUpdateProduct() throws Exception {
    long productId = 1L;
    Product updatedProduct = new Product();
    when(productService.updateProduct(eq(productId), any())).thenReturn(updatedProduct);

    mockMvc.perform(put("/products/{id}", productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"name\": \"Updated Product\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Updated Product")));
  }

  @Test
  void testDeleteProduct() throws Exception {
    long productId = 1L;

    mockMvc.perform(delete("/products/{id}", productId))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(productId)));
  }

  @Test
  void testGetAllProductsByOwnerId() throws Exception {
    long ownerId = 1L;
    List<Product> products = Arrays.asList(new Product(), new Product());
    when(productService.findAllProductsByOwnerId(ownerId)).thenReturn(products);

    mockMvc.perform(get("/products/owner/{ownerId}", ownerId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testAddProductToOwner() throws Exception {
    long ownerId = 1L;
    long productId = 1L;
    Product product = new Product();
    when(productService.addProductToOwner(ownerId, productId)).thenReturn(product);

    mockMvc.perform(post("/products/owner/{ownerId}/add/{productId}", ownerId, productId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(productId)));
  }

  @Test
  void testRemoveProductFromOwner() throws Exception {
    long ownerId = 1L;
    long productId = 1L;

    mockMvc.perform(delete("/products/owner/{ownerId}/remove/{productId}", ownerId, productId))
            .andExpect(status().isOk());
  }

  @Test
  void testUpdateProductForOwner() throws Exception {
    long ownerId = 1L;
    long productId = 1L;
    Product newProduct = new Product();
    when(productService.updateProductForOwner(eq(ownerId),
            eq(productId), any())).thenReturn(newProduct);

    mockMvc.perform(put("/products/owner/{ownerId}/update/{productId}", ownerId, productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"name\": \"New Product Name\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("New Product Name")));
  }
}
