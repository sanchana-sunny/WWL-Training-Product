package com.Product.service;



import org.springframework.stereotype.Service;

import com.Product.entity.Product;

@Service
public interface ProductService {
	
	public Product saveProduct(Product product);
	
	
	

}
