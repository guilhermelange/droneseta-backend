package com.udesc.droneseta.model.dto;

import com.udesc.droneseta.model.Customer;

public class AuthResponseDTO {
    private String token;
    private Customer customer;

    public AuthResponseDTO(String token, Customer customer) {
        this.token = token;
        this.customer = customer;
    }

    public AuthResponseDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
