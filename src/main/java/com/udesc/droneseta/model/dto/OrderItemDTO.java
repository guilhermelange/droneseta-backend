package com.udesc.droneseta.model.dto;

import jakarta.validation.constraints.NotNull;

public class OrderItemDTO {

    public OrderItemDTO() {
    }

    public OrderItemDTO(Integer quantity, Integer product_id, double price) {
        this.quantity = quantity;
        this.product_id = product_id;
        this.price = price;
    }

    @NotNull()
    private Integer quantity;

    @NotNull()
    private Integer product_id;

    @NotNull()
    private double price;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
