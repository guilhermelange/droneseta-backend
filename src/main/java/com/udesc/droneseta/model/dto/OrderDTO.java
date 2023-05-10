package com.udesc.droneseta.model.dto;

import com.udesc.droneseta.model.enumerator.OrderStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import com.udesc.droneseta.model.OrderItem;

public class OrderDTO {

    @NotNull()
    private double price;

    private OrderStatus status;

    @NotNull()
    private Integer customer_id;

    private List<OrderItemDTO> items;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

}
