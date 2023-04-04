package com.udesc.droneseta.repository;

import com.udesc.droneseta.model.dto.CustomerAllDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    @Query("SELECT c.id as id, c.name as name, c.cpf as cpf, c.creditCard as creditCard from Customer c")
    List<CustomerAllDTO> findAllProjectedBy();

    Customer findFirstByCpfEquals(String cpf);
}
