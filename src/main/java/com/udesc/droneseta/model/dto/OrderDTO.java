package com.udesc.droneseta.model.dto;

import com.udesc.droneseta.model.enumerator.OrderStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

public class OrderDTO {
    @NotNull()
    private double price;

    private OrderStatus status;

    @NotNull()
    private Integer customer_id;

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
}
