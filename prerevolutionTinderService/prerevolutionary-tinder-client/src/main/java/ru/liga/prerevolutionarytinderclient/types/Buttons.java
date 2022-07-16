package ru.liga.prerevolutionarytinderclient.types;

public enum Buttons {
    LEFT_BUTTON("<--Лево"),
    RIGHT_BUTTON("Право-->"),
    MENU_BUTTON("Меню"),
    СHANGE_BUTTON("Изменить"),

    SIR("Сударь"),
    MADAM("Сударыня"),

    LADIES("Сударыни"),
    GENTLEMEN("Судари"),
    ALL("Все");


    private final String buttonName;

    Buttons(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
