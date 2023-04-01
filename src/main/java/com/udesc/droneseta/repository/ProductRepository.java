package com.udesc.droneseta.repository;

import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findAllByNameContainingIgnoreCase(String name);
}
