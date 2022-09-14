package com.Product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.Product.entity.Product;
import com.Product.repository.ProducttRepository;
import com.Product.service.ProductService;

@SpringBootTest
class ProductApplicationTests {

	
	@Autowired
	private ProductService service;

	@MockBean
	private ProducttRepository repository;

	@Test
	public void getProductTest() {
		when(repository.findAll()).thenReturn(Stream
				.of(new Product(101, "iphone","good", 31000), new Product (102, "pencil","nice", 10)).collect(Collectors.toList()));
		assertEquals(2, repository.findAll().size());
	}		
		
	@Test
	public void addProductTest() {
		Product product = new Product(103, "chair", "perfect",500);
		when(repository.save(product)).thenReturn(product);
		assertEquals(product, service.saveProduct(product));
	}
}
