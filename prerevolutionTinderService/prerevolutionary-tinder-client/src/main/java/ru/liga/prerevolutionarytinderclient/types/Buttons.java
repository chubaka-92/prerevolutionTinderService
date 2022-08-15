package ru.liga.prerevolutionarytinderclient.types;

import lombok.Getter;

@Getter
public enum Buttons {
    LEFT_BUTTON("<--Лево"),
    RIGHT_BUTTON("Право-->"),
    MENU_BUTTON("Меню"),
    MY_PROFILE("Моя анкета"),
    SEARCH_BUTTON("Поиск"),
    FAVORITE_BUTTON("Любимцы"),

    CHANGE_BUTTON("Изменить"),

    SIR("Сударъ"),
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
