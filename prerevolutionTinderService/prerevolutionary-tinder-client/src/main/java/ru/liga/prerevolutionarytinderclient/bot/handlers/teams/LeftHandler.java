package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CommandHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;
import ru.liga.prerevolutionarytinderclient.utils.MessageTemplate;

@Component
public class LeftHandler implements CommandHandler {

    private static final int START_PAGE = 0;
    private static final int ONE_POSITION = 1;

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
    UserDataCache userDataCache;

    @Autowired
    MessageTemplate messageTemplate;

    @Override
    public String getName() {
        return "<--Лево";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case SEARCH_FAVORITES:
                personRequest.setCurrentPage(getNextPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));
                PersonResponse candidate = requestServer.getCandidateFavorites(userId, personRequest.getCurrentPage());
                reLoadPersonRequest(candidate);
                tinderBot.getPhoto(messageTemplate.createMessagePhoto(userId, candidate, replyKeyboardMaker.getSearchKeyboard()));
                return null;

            case SHOW_FAVORITES:
                personRequest.setCurrentPage(getPreviousPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

                PersonResponse favorite = requestServer.getFavorites(userId, personRequest.getCurrentPage());
                personRequest.setCurrentPage(favorite.getCurrentPage());
                personRequest.setTotalPage(favorite.getTotalPage());

                tinderBot.getPhoto(messageTemplate.createMessagePhoto(userId, favorite, replyKeyboardMaker.getFavoriteKeyboard()));
                SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
                return sendMessage;
        }

        return null;
    }

    private void reLoadPersonRequest(PersonResponse favorite) {
        personRequest.setCurrentPage(favorite.getCurrentPage());
        personRequest.setTotalPage(favorite.getTotalPage());
        personRequest.setCurrentPersonId(favorite.getId());
    }

    private int getNextPage(int totalPage, int currentPage) {
        if (totalPage - ONE_POSITION > currentPage) {
            return currentPage + ONE_POSITION;
        }
        return START_PAGE;
    }

    private int getPreviousPage(int totalPage, int currentPage) {
        if (currentPage > START_PAGE) {
            return currentPage - 1;
        }
        return totalPage - 1;
    }
}
