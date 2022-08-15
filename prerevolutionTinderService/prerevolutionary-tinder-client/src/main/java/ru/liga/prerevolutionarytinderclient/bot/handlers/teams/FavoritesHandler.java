package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CommandHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;
import ru.liga.prerevolutionarytinderclient.utils.MessageTemplate;

@Slf4j
@Component
public class FavoritesHandler implements CommandHandler {

    private static final int START_PAGE = 0;
    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    PersonRequest personRequest;

    @Autowired
    RequestServer requestServer;

    @Autowired
    DataCache dataCache;

    @Autowired
    @Lazy
    TinderBot tinderBot;

    @Autowired
    MessageTemplate messageTemplate;

    @Override
    public String getName() {
        return "Любимцы";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {

        dataCache.setUsersCurrentBotState(userId, BotState.SHOW_FAVORITES);

        personRequest = requestServer.getProfile(userId);

        PersonResponse favorite = requestServer.getFavorites(userId, START_PAGE);
        personRequest.setCurrentPage(favorite.getCurrentPage());
        personRequest.setTotalPage(favorite.getTotalPage());

        tinderBot.getPhoto(messageTemplate.createMessagePhoto(userId, favorite, replyKeyboardMaker.getFavoriteKeyboard()));

        SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
        return sendMessage;
    }
}
