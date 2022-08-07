package ru.liga.prerevolutionarytinderclient.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteStatus {
    RECIPROCITY("Взаимность."),
    LIKE_BE_ME("Любим вами."),
    YOU_LIKE_ME("Вы любимы.");

    private final String translate;
}
