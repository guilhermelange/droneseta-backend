package com.udesc.droneseta.model.enumerator;

public enum OrderStatus {

    PENDENTE(1, "Pendente"),
    CONFIRMADO(2, "Confirmado"),
    TRANSITO(3, "Em Trânsito"),
    ENTREGUE(4, "Entregue"),
    CANCELADO(5, "Cancelado");

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

    public static OrderStatus fromString(String description) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue().equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status Inválido: " + description);
    }
}