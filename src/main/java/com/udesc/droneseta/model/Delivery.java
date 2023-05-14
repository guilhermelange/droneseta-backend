package com.udesc.droneseta.model;

import com.udesc.droneseta.model.enumerator.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    public Delivery(Integer id, Integer quantity, DeliveryStatus status) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
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
    private DeliveryStatus status;

    private LocalDateTime dateTime;

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

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Delivery{" + "id=" + id + ", quantity=" + quantity + ", status=" + status + ", dateTime=" + dateTime + '}';
    }

}