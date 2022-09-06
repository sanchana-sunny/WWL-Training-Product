package com.Product.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.Product.entity.Product;
import com.Product.repository.ProducttRepository;
import com.Product.service.FileDownloadUtil;

import com.Product.service.ProductService;
import com.Product.service.RandomStringUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")

public class ProductController {
	@Autowired
	private ProducttRepository productrepository;
	
	@Autowired
	private ProductService productservice;
	
	//View product list using Rest API
	@GetMapping("/products")
	public List<Product> getAllTicket(){
		return productrepository.findAll();
	}
	
	
	//Add product using Rest API
	@PostMapping("/addProducts")
	public ResponseEntity<String> createProduct(Product product, @RequestParam(value ="image",required = false) MultipartFile multipartFile) throws IOException{
	
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		String fileCode = RandomStringUtils.randomAlphanumeric(4);
		
		product.setImg(fileCode + fileName);
        
		productservice.saveProduct(product);
        String uploadDir = "./Images/";
        Path uploadPath = Paths.get(uploadDir);
        
 
        if(!Files.exists(uploadPath)) {
        	try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
        Path filePath = uploadPath.resolve(fileCode+fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
 
        }catch(IOException e)
        {
        	System.out.println(e);
        }
        
        productservice.saveProduct(product);

		return ResponseEntity.ok("Product inserted successfully");	
	}
	

	
	 @GetMapping("/downloadFile/{fileCode}")
	    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode ) {
	        FileDownloadUtil downloadUtil = new FileDownloadUtil();
	         
	        Resource resource = null;
	        try {
	            resource = downloadUtil.getFileAsResource(fileCode);
	        } catch (IOException e) {
	            return ResponseEntity.internalServerError().build();
	        }
	         
	        if (resource == null) {
	            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
	        }
	         
	        String contentType = "image/png";
	    
	        String headerValue = "inline; filename=\"" + resource.getFilename() + "\"";
	         
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(resource);       
	    }
}
