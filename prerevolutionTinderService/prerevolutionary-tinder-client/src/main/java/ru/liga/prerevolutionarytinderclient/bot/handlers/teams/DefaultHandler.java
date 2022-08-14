package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CommandHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.types.BotState;
import ru.liga.prerevolutionarytinderclient.utils.MessageTemplate;

@Component
@RequiredArgsConstructor
public class DefaultHandler implements CommandHandler {

    private final UserDataCache userDataCache;
    private final PersonRequest personRequest;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final MessageTemplate messageTemplate;

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case ASK_NAME:
                personRequest.setName(inputText);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_DESK);
                return messageTemplate.createMessage(chatId, "Раскажите о себе");
            case ASK_DESK:
                personRequest.setDescription(inputText);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_DESK);
                return messageTemplate.createMessageAndReplyMarkup(chatId, "Кого предпочитаете?", inlineKeyboardMaker.getSearchGenderInlineMessageButtons());
        }

        return null;
    }

}