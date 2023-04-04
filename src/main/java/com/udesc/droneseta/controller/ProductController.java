package com.udesc.droneseta.controller;

import com.udesc.droneseta.model.error.ApplicationException;
import com.udesc.droneseta.model.Product;
import com.udesc.droneseta.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
	public ResponseEntity<?> findAll(@RequestParam(value = "name", defaultValue = "") String name){
		if (name.isEmpty()) {
			return ResponseEntity.ok().body(repository.findAll());
		} else {
			return ResponseEntity.ok().body(repository.findAllByNameContainingIgnoreCase(name));
		}
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

	@PostMapping("/image/{id}")
	public ResponseEntity<?> updateImg(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws Exception {
		Optional<Product> product = repository.findById(id);
		if (product.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}

		if (file.isEmpty()) {
			throw new ApplicationException("Por favor, selecione um arquivo", HttpStatus.NOT_FOUND);
		}

		String fileLocation = (new File("")).getAbsolutePath() + "\\src\\main\\resources\\static\\" + product.get().getImg();

		file.transferTo(new File(fileLocation));

		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> findAll(@PathVariable Integer id) throws Exception {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
