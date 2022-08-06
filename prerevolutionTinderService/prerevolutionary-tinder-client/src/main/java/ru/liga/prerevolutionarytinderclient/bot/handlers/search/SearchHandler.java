package ru.liga.prerevolutionarytinderclient.bot.handlers.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonsResponse;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
@Component
public class SearchHandler {
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

    public SendMessage processUsersInput(BotState botState, Message message) {
        long userId = message.getFrom().getId();
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (botState.equals(BotState.SEARCH_FAVORITES)) {

            personRequest = requestServer.getProfile(userId);

            PersonsResponse favorite = requestServer.getCandidateFavorites(userId, 0);
            personRequest.setCurrentPage(favorite.getCurrentPage());
            personRequest.setTotalPage(favorite.getTotalPage());

            SendPhoto sendPhoto = getSendPhoto(userId, favorite);

            tinderBot.getPhoto(sendPhoto);

            SendMessage sendMessage = new SendMessage(chatId,favorite.toString());
            //sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            return sendMessage;
        }

        if (botState.equals(BotState.SEARCH_LIKE)) {
            personRequest.setCurrentPage(getNextPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

            PersonsResponse favorite = requestServer.getCandidateFavorites(userId,personRequest.getCurrentPage());
            personRequest.setCurrentPage(favorite.getCurrentPage());
            personRequest.setTotalPage(favorite.getTotalPage());

            SendPhoto sendPhoto = getSendPhoto(userId, favorite);

            tinderBot.getPhoto(sendPhoto);
            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            //sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            return sendMessage;
        }

        if (botState.equals(BotState.SEARCH_DONT_LIKE)) {
            personRequest.setCurrentPage(getPreviousPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

            PersonsResponse favorite = requestServer.getCandidateFavorites(userId,personRequest.getCurrentPage());
            personRequest.setCurrentPage(favorite.getCurrentPage());
            personRequest.setTotalPage(favorite.getTotalPage());

            SendPhoto sendPhoto = getSendPhoto(userId, favorite);

            tinderBot.getPhoto(sendPhoto);
            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            //sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            return sendMessage;
        }
        return null;
    }


    private SendPhoto getSendPhoto(Long userId, PersonsResponse favorite) {
        InputStream inputStream = new ByteArrayInputStream(favorite.getPicture());
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(inputStream, "picture.jpg"));
        sendPhoto.setChatId(String.valueOf(userId));
        sendPhoto.setCaption(favorite.getCaption());
        sendPhoto.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
        return sendPhoto;
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
