package com.udesc.droneseta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {
    public Product() {}

    public Product(Integer id, String name, String description, String size, double price, long stock, String img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.img = img;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    @Size(min=3, message="O nome deve ter pelo menos 3 caracteres")
    private String name;

    @Column(nullable = false)
    @NotEmpty()
    private String description;

    @Column(nullable = false)
    @NotEmpty()
    private String size;

    @Column(nullable = false)
    @NotNull()
    private double price;

    @Column(nullable = false)
    @NotNull()
    private long stock;

    @Column(nullable = false, columnDefinition = "text default ''")
    @NotEmpty()
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
