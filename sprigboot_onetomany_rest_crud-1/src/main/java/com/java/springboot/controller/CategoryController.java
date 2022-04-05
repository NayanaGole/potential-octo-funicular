package com.java.springboot.controller;

import com.java.springboot.exception.ResourceNotFoundException;

import com.java.springboot.model.Category;
import com.java.springboot.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CategoryController {
	@Autowired
    private CategoryRepository categoryRepository;
	
	 @GetMapping("/categories")
	    public Page<Category> getAllCategories(Pageable pageable) {
	        return categoryRepository.findAll(pageable);
	    }

	    @PostMapping("/categories")
	    public Category createPost(@Valid @RequestBody Category category) {
	        return categoryRepository.save(category);
	    }
	    
	    @PutMapping("/categories/{categoryId}")
	    public Category updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category categoryRequest) {
	        return categoryRepository.findById(categoryId).map(category -> {
	            category.setTitle(categoryRequest.getTitle());
	            category.setDescription( categoryRequest.getDescription());
	            category.setContent( categoryRequest.getContent());
	            return  categoryRepository.save( category);
	        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
	    }
	    
	    @DeleteMapping("/categories/{categoryId}")
	    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
	        return categoryRepository.findById(categoryId).map(category -> {
	        	categoryRepository.delete(category);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
	    }

}
