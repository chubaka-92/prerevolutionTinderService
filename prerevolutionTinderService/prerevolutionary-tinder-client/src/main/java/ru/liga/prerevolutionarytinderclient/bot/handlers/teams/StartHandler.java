package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CommandHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.types.BotState;

@Component
public class StartHandler implements CommandHandler {

    @Autowired
    UserDataCache userDataCache;

    @Autowired
    InlineKeyboardMaker inlineKeyboardMaker;

    @Autowired
    PersonRequest personRequest;

    @Override
    public String getName() {
        return "/start";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {

        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);

        personRequest.setId(userId);
        SendMessage sendMessage = new SendMessage(chatId, "Привет, нужно заполнить анкету \n Какой ваш пол?");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getGenderInlineMessageButtons());
        return sendMessage;
    }
}