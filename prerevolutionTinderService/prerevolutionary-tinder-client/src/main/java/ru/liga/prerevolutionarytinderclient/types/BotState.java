package ru.liga.prerevolutionarytinderclient.types;

/**
 * Возможные состояния бота
 */

public enum BotState {
    ASK_DESTINY,
    ASK_NAME,
    ASK_DESK,
    ASK_PREFERENCE,
    FILLING_PROFILE,
    PROFILE_FILLED,
    SHOW_USER_PROFILE,
    SHOW_HELP_MENU;
}