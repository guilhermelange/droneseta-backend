package com.udesc.droneseta.repository;

import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
