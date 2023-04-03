package com.udesc.droneseta.repository;

import com.udesc.droneseta.model.dto.CustomerAllDTO;
import com.udesc.droneseta.model.dto.OrderPendingDTO;
import com.udesc.droneseta.model.enumerator.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>{

    List<Order> findByCustomer(Customer customer);

    List<Order> findAllByStatusIn(List<OrderStatus> status);

    @Query("SELECT o.id as id, o.price as price, o.status as status, c.name as name, c.creditCard as creditCard FROM Order o JOIN o.customer c WHERE o.status IN :orderStatus")
    List<OrderPendingDTO> findAllOrderFilter(List<OrderStatus> orderStatus);
}
