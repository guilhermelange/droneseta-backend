package com.udesc.droneseta.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.udesc.droneseta.model.dto.OrderDTO;
import com.udesc.droneseta.model.dto.OrderStatusDTO;
import com.udesc.droneseta.model.enumerator.OrderStatus;
import com.udesc.droneseta.repository.CustomerRepository;
import com.udesc.droneseta.service.OrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udesc.droneseta.model.error.ApplicationException;
import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.repository.OrderRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("order")
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
	public ResponseEntity<?> findAll(@RequestParam(value = "status", defaultValue = "") String status){
		if (status.isEmpty()) {
			return ResponseEntity.ok().body(repository.findAll());
		} else {
			List<String> statusStringSearch = Arrays.stream(status.split(",")).toList();
			List<OrderStatus> statusSearch = statusStringSearch.stream().map(item -> (OrderStatus.valueOf(item))).collect(Collectors.toList());
			return ResponseEntity.ok().body(repository.findAllOrderFilter(statusSearch));
		}
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

	@PatchMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable Integer id, @Valid @RequestBody OrderStatusDTO order) throws Exception {
		Optional<Order> findOrder = repository.findById(id);

		if (findOrder.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}

		Order currentOrder = findOrder.get();
		currentOrder.setStatus(order.getStatus());
		Order savedOrder = repository.save(currentOrder);

		return ResponseEntity.ok().body(savedOrder);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAll(@PathVariable Integer id) throws Exception {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<?> findByCustomer(@PathVariable Integer id) throws Exception {
		Customer customer = new Customer();
		customer.setId(id);
		return ResponseEntity.ok().body(repository.findByCustomer(customer));
	}

	@GetMapping(value = "/report")
	public ResponseEntity<?> generateReport() throws Exception {
		OrderReportService service = new OrderReportService(repository);
		String filename = service.generateReport();
		File file = new File(filename);

		byte[] bytes = Files.readAllBytes(file.toPath());
		ByteArrayResource resource = new ByteArrayResource(bytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OrderReport.xlsx");
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(bytes.length)
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(resource);
	}
}
