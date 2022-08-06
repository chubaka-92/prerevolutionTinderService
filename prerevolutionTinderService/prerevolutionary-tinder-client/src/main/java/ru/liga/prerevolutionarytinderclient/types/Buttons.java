package ru.liga.prerevolutionarytinderclient.types;

public enum Buttons {
    LEFT_BUTTON("<--Лево"),
    RIGHT_BUTTON("Право-->"),
    MENU_BUTTON("Меню"),
    MY_PROFILE("Моя анкета"),
    SEARCH_BUTTON("Поиск"),
    FAVORITE_BUTTON("Любимцы"),

    SEARCH_LEFT_BUTTON("<-Лево"),
    SEARCH_RIGHT_BUTTON("Право->"),

    CHANGE_BUTTON("Изменить"),

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
