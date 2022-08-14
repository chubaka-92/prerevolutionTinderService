package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

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
import ru.liga.prerevolutionarytinderclient.types.FavoriteStatus;
import ru.liga.prerevolutionarytinderclient.utils.MessageTemplate;


@Component
public class RightHandler implements CommandHandler {

    private static final int START_PAGE = 0;
    private static final int ONE_POSITION = 1;
    private static final int TWO_POSITION = 2;
    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    PersonRequest personRequest;

    @Autowired
    RequestServer requestServer;

    @Autowired
    DataCache dataCache;

    @Autowired
    MessageTemplate messageTemplate;

    @Autowired
    @Lazy
    TinderBot tinderBot;

    @Override
    public String getName() {
        return "Право-->";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {


        BotState botState = dataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case SEARCH_FAVORITES:
                //Сделайть сейв на серваке и ждем в ответ статус(Взаимно) и отправляем его в чат
                String statusFavorite = requestServer.saveLikeFavorites(userId, personRequest.getCurrentPersonId());
                if (FavoriteStatus.RECIPROCITY.getTranslate().equals(statusFavorite)) {
                    SendMessage sendMessage = new SendMessage(chatId, statusFavorite);
                    //Кидаем сообщение если Взаимная лаф
                    tinderBot.getMessage(sendMessage);
                }
                //получаем следующуую анкету...
                PersonResponse candidate = getNextPageAfterLike(userId);
                reLoadPersonRequest(candidate);
                //создаем и шлем фотку
                tinderBot.getPhoto(messageTemplate.createMessagePhoto(userId, candidate, replyKeyboardMaker.getSearchKeyboard()));
                break;

            case SHOW_FAVORITES:
                personRequest.setCurrentPage(getNextPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

                PersonResponse favorite = requestServer.getFavorites(userId, personRequest.getCurrentPage());
                personRequest.setCurrentPage(favorite.getCurrentPage());
                personRequest.setTotalPage(favorite.getTotalPage());

                tinderBot.getPhoto(messageTemplate.createMessagePhoto(userId, favorite, replyKeyboardMaker.getFavoriteKeyboard()));
                SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
                return sendMessage;
        }
        return null;
    }

    private PersonResponse getNextPageAfterLike(Long userId) {
        if (personRequest.getTotalPage() - TWO_POSITION > personRequest.getCurrentPage()) { // проверяем будет ли запись с номером текущей записи в списке.
            return requestServer.getCandidateFavorites(userId, personRequest.getCurrentPage());
        } else if (personRequest.getTotalPage() - ONE_POSITION > 0) { // проверяем есть ли хоть какие то записи. е
            return requestServer.getCandidateFavorites(userId, START_PAGE);
        } else {
            throw new RuntimeException("Кончились кандидаты(");
        }
    }

    private void reLoadPersonRequest(PersonResponse favorite) {
        personRequest.setCurrentPage(favorite.getCurrentPage());
        personRequest.setTotalPage(favorite.getTotalPage());
        personRequest.setCurrentPersonId(favorite.getId());
    }

    private int getNextPage(int totalPage, int currentPage) {
        if (totalPage - 1 > currentPage) {
            return currentPage + 1;
        }
        return START_PAGE;
    }

}

