package com.udesc.droneseta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "delivery")
public class Delivery {

    public Delivery() {
    }

    public Delivery(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    @NotNull()
    private int quantity;

    @Column(nullable = false)
    @Value("1")
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" + "id=" + id + ", quantity=" + quantity + ", status=" + status + '}';
    }

}