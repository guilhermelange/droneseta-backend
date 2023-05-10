package com.udesc.droneseta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udesc.droneseta.model.OrderItem;
import com.udesc.droneseta.model.dto.BestSellerDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

    @Query(value = "SELECT product_id, "
            + "sum(quantity) as quantity, "
            + "sum(quantity * price) as value "
            + "from order_item group by product_id order by sum(quantity) limit :limit", nativeQuery = true)
    public List<BestSellerDTO> findBestN(@Param("limit") int limit);

}
