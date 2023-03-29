package com.udesc.droneseta.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udesc.droneseta.common.error.ApplicationException;
import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.repository.AddressRepository;
import com.udesc.droneseta.repository.CustomerRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody Customer customer) throws Exception {
		Example<Customer> example = Example.of(customer);
		System.out.println(customer);
		Optional<Customer> findCustomer = repository.findOne(example);
		
		if (!findCustomer.isEmpty()) {
			throw new ApplicationException("CPF já utilizado", HttpStatus.BAD_REQUEST);
		}
		
		Customer savedCustomer = repository.save(customer);
		return ResponseEntity.ok().body(savedCustomer);
	}
		
	@GetMapping("")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok().body(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUnique(@PathVariable Integer id) throws Exception {
		Optional<Customer> customer = repository.findById(id);
		if (customer.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(customer.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Customer customer) throws Exception {
		Optional<Customer> findCustomer = repository.findById(id);
		
		if (findCustomer.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}
		
		Customer savedCustomer = repository.save(customer);
		return ResponseEntity.ok().body(savedCustomer);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> findAll(@PathVariable Integer id) throws Exception {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
