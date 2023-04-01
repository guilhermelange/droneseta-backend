package com.udesc.droneseta.model.enumerator;

public enum DeliveryStatus {

    AGUARDANDO(1, "Aguardando"),
    TRANSITO(2, "Em Trânsito"),
    FINALIZADO(3, "Finalizado");

    private final int key;
    private final String value;

    DeliveryStatus(int key, String value) {
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
