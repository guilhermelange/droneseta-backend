package com.udesc.droneseta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.Customer;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>{

    List<Order> findByCustomer(Customer customer);

}
