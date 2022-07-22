package ru.liga.prerevolutionarytinderclient.bot.cache;

import ru.liga.prerevolutionarytinderclient.types.BotState;

public interface DataCache {
    void setUsersCurrentBotState(Long userId, BotState botState);

    BotState getUsersCurrentBotState(Long userId);

}
