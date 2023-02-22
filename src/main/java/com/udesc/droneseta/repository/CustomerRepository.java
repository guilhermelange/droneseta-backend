package com.udesc.droneseta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
