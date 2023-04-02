package com.udesc.droneseta.controller;

import java.util.Optional;

import com.udesc.droneseta.model.dto.OrderDTO;
import com.udesc.droneseta.model.enumerator.OrderStatus;
import com.udesc.droneseta.repository.CustomerRepository;
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

import com.udesc.droneseta.model.error.ApplicationException;
import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.repository.OrderRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody OrderDTO order) throws Exception {
		Optional<Customer> customer = customerRepository.findById(order.getCustomer_id());

		if (customer.isEmpty()) {
			throw new ApplicationException("Invalid Customer ID", HttpStatus.NOT_FOUND);
		}

		Order orderSave = new Order();
		orderSave.setPrice(order.getPrice());
		orderSave.setCustomer(customer.get());
		orderSave.setStatus(OrderStatus.PENDENTE);
		if (order.getStatus() != null)
			orderSave.setStatus(order.getStatus());
		Order savedOrder = repository.save(orderSave);

		return ResponseEntity.ok().body(savedOrder);
	}

	@GetMapping("")
	public ResponseEntity<?> findAll(){
            return ResponseEntity.ok().body(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findUnique(@PathVariable Integer id) throws Exception {
            Optional<Order> order = repository.findById(id);
            if (order.isEmpty()) {
                throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok().body(order.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Order order) throws Exception {
            Optional<Order> findOrder = repository.findById(id);

            if (findOrder.isEmpty()) {
                    throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
            }

            Order savedOrder = repository.save(order);

            return ResponseEntity.ok().body(savedOrder);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAll(@PathVariable Integer id) throws Exception {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
	}

        @GetMapping("/customer")
	public ResponseEntity<?> findByCustomer(@Valid @RequestBody Customer customer) throws Exception {
            return ResponseEntity.ok().body(repository.findByCustomer(customer));
	}
}
