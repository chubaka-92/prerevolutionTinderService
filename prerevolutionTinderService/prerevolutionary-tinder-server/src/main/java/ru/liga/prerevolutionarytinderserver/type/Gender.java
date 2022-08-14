package ru.liga.prerevolutionarytinderserver.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    SIR("Сударъ"),
    MADAM("Сударыня"),

    LADIES("Сударыни"),
    GENTLEMEN("Судари"),
    ALL("Все");

    private final String translate;

}
