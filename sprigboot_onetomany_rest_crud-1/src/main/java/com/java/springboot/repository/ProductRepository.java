package com.java.springboot.repository;


import com.java.springboot.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByCategoryId(Long postId, Pageable pageable);
    Optional<Product> findByIdAndCategoryId(Long id, Long postId);

}
