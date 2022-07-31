package ru.liga.prerevolutionarytinderclient.bot.handlers.favorits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;


@Slf4j
@Component
public class FavoritesHandler {
    private static final int START_PAGE = 0;
    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    PersonRequest personRequest;

    @Autowired
    RequestServer requestServer;

    @Autowired
    DataCache dataCache;

    public SendMessage processUsersInput(BotState botState, Message message) {
        long userId = message.getFrom().getId();
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (botState.equals(BotState.SHOW_FAVORITES)) {
            personRequest = requestServer.getProfile(userId);

            PersonRequest favorite = requestServer.getFavorites(userId, START_PAGE);
            personRequest.setCurrentPage(favorite.getCurrentPage());
            personRequest.setTotalPage(favorite.getTotalPage());

            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            return sendMessage;
        }

        if (botState.equals(BotState.SHOW_FAVORITES_NEXT)) {
            personRequest.setCurrentPage(getNextPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

            PersonRequest favorite = requestServer.getFavorites(userId,personRequest.getCurrentPage());

            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            return sendMessage;
        }

        if (botState.equals(BotState.SHOW_FAVORITES_PREVIOUS)) {
            personRequest.setCurrentPage(getPreviousPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

            PersonRequest favorite = requestServer.getFavorites(userId,personRequest.getCurrentPage());

            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            return sendMessage;
        }
        return null;
    }

    private int getNextPage(int totalPage, int currentPage) {
        if(totalPage-1>currentPage){
            return currentPage+1;
        }
        return START_PAGE;
    }

    private int getPreviousPage(int totalPage, int currentPage) {
        if(currentPage>START_PAGE){
            return currentPage-1;
        }
        return totalPage-1;
    }
}
