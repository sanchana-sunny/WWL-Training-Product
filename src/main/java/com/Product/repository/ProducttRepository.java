package com.Product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Product.entity.Product;

@Repository
public interface ProducttRepository extends JpaRepository<Product,Long> {
	

}
