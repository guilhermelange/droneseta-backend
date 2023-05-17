package com.udesc.droneseta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.OrderItem;
import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.dto.BestSellerDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

    @Query(value = "SELECT product_id, "
            + "sum(quantity) as quantity, "
            + "sum(quantity * oi.price) as value "
            + "from order_item oi "
            + "join orders o "
            + "on o.id = oi.order_id "
            + "where status not in (0,4) "
            + "group by product_id order by sum(quantity) limit :limit", nativeQuery = true)
    public List<BestSellerDTO> findBestN(@Param("limit") int limit);

    List<OrderItem> findByOrder(Order order);

}
