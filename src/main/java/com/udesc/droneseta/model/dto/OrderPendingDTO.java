package com.udesc.droneseta.model.dto;

import com.udesc.droneseta.model.enumerator.OrderStatus;

public interface OrderPendingDTO {
    Integer getId();
    String getName();
    double getPrice();
    OrderStatus getStatus();
    String getCreditCard();
}
