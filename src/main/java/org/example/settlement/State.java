package org.example.settlement;

public enum State {
    CLOSE("Закрыт"),
    OPEN("Открыт"),
    RESERVED("Зарезервировано"),
    DELETE("Удален");

    private final String decription;

    State(String desc) {
        this.decription = desc;
    }
}
