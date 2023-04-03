package com.udesc.droneseta.model.dto;

import com.udesc.droneseta.model.enumerator.OrderStatus;

public class OrderStatusDTO {
    private OrderStatus status;

    public OrderStatusDTO() {}

    public OrderStatusDTO(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
