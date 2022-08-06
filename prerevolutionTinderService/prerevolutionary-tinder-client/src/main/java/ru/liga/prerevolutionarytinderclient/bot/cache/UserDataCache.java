package ru.liga.prerevolutionarytinderclient.bot.cache;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytinderclient.types.BotState;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@Component
public class UserDataCache implements DataCache {
    private Map<Long, BotState> usersBotStates;

    @Override
    public void setUsersCurrentBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(Long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_DESTINY;
        }

        return botState;
    }
}