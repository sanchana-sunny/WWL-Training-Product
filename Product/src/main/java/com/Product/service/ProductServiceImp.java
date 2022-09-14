package com.Product.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Product.entity.Product;
import com.Product.repository.ProducttRepository;
@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProducttRepository productrepository;
	


	@Override
	public Product saveProduct(Product product) {
		return productrepository.save( product);
		
	}

	


}
