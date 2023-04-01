package com.udesc.droneseta.model.enumerator;

public enum OrderStatus {

    PENDENTE(1, "Pendente"),
    CONFIRMADO(2, "Confirmado"),
    TRANSITO(3, "Em Trânsito"),
    ENTREGUE(4, "Entregue");

    private final int key;
    private final String value;

    OrderStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}