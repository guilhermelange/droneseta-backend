package com.udesc.droneseta.controller;

import com.udesc.droneseta.common.error.ApplicationException;
import com.udesc.droneseta.model.Product;
import com.udesc.droneseta.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductRepository repository;
	
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody Product product) throws Exception {
		Example<Product> example = Example.of(product);
		Optional<Product> findProduct = repository.findOne(example);
		
		if (!findProduct.isEmpty()) {
			throw new ApplicationException("Produto indisponível", HttpStatus.BAD_REQUEST);
		}
		
		Product savedProduct = repository.save(product);
		return ResponseEntity.ok().body(savedProduct);
	}
		
	@GetMapping("")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok().body(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUnique(@PathVariable Integer id) throws Exception {
		Optional<Product> product = repository.findById(id);
		if (product.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(product.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Product product) throws Exception {
		Optional<Product> findProduct = repository.findById(id);
		
		if (findProduct.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}
		
		Product savedProduct = repository.save(product);
		return ResponseEntity.ok().body(savedProduct);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> findAll(@PathVariable Integer id) throws Exception {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
