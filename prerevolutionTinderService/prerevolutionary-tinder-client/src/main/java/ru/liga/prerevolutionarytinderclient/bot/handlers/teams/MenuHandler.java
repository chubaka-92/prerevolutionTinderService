package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CommandHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;

@Component
public class MenuHandler implements CommandHandler {

    @Autowired
    UserDataCache userDataCache;

    @Autowired
    InlineKeyboardMaker inlineKeyboardMaker;

    @Autowired
    PersonRequest personRequest;

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Override
    public String getName() {
        return "Меню";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {

        SendMessage sendMessage = new SendMessage(chatId, "Главное меню.");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
        return sendMessage;
    }
}
