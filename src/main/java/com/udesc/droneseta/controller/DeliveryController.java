package com.udesc.droneseta.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.udesc.droneseta.model.Delivery;
import com.udesc.droneseta.repository.DeliveryRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	private DeliveryRepository repository;

	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody Delivery delivery) throws Exception {
            Delivery savedDelivery = repository.save(delivery);

            return ResponseEntity.ok().body(savedDelivery);
	}

	@GetMapping("")
	public ResponseEntity<?> findAll(){
            return ResponseEntity.ok().body(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findUnique(@PathVariable Integer id) throws Exception {
            Optional<Delivery> delivery = repository.findById(id);
            if (delivery.isEmpty()) {
                throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(delivery.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Delivery delivery) throws Exception {
            Optional<Delivery> findDelivery = repository.findById(id);

            if (findDelivery.isEmpty()) {
                    throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
            }

            Delivery savedDelivery = repository.save(delivery);

            return ResponseEntity.ok().body(savedDelivery);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAll(@PathVariable Integer id) throws Exception {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
	}
}
