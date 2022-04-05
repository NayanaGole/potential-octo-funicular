package com.java.springboot.controller;
import com.java.springboot.exception.ResourceNotFoundException;

import com.java.springboot.model.Product;
import com.java.springboot.repository.CategoryRepository;
import com.java.springboot.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class ProductController {
	 @Autowired
	    private ProductRepository productRepository;

	    @Autowired
	    private CategoryRepository categoryRepository;

	    @GetMapping("/categories/{categoryId}/products")
	    public Page<Product> getAllProductsByCategoryId(@PathVariable (value = "categoryId") Long categoryId,
	                                                Pageable pageable) {
	        return productRepository.findByCategoryId(categoryId, pageable);
	    }
		
	    @PostMapping("/categories/{categoryId}/products")
	    public Product createProduct(@PathVariable (value = "categoryId") Long categoryId,
	                                 @Valid @RequestBody Product product) {
	        return categoryRepository.findById(categoryId).map(category -> {
	            product.setCategory(category);
	            return productRepository.save(product);
	        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
	    }
	        
	        @PutMapping("/products/{categoryId}/products/{productId}")
	        public Product updateProduct(@PathVariable (value = "categoryId") Long categoryId,
	                                     @PathVariable (value = "productId") Long productId,
	                                     @Valid @RequestBody Product productRequest) {
	            if(!categoryRepository.existsById(categoryId)) {
	                throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
	            }
	            
	            return productRepository.findById(productId).map(product -> {
	                product.setText(productRequest.getText());
	                return productRepository.save(product);
	            }).orElseThrow(() -> new ResourceNotFoundException("ProductId " + productId + "not found"));
	        }
	        
	        @DeleteMapping("/categories/{categoryId}/products/{productId}")
	        public ResponseEntity<?> deleteProduct(@PathVariable (value = "categoryId") Long categoryId,
	                                  @PathVariable (value = "productId") Long productId) {
	            return productRepository.findByIdAndCategoryId(productId, categoryId).map(product -> {
	                productRepository.delete(product);
	                return ResponseEntity.ok().build();
	            }).orElseThrow(() -> new ResourceNotFoundException("product not found with id " + productId + " and categoryId " + categoryId));
	        }
	    

}
