package com.udesc.droneseta.model.dto;

import jakarta.validation.constraints.NotNull;

public class AuthDTO {
    @NotNull()
    private String cpf;

    @NotNull()
    private String password;

    public AuthDTO(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public AuthDTO() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
