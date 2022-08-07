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
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;
import ru.liga.prerevolutionarytinderclient.types.FavoriteStatus;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
@Component
public class SearchHandler {
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
    @Lazy
    TinderBot tinderBot;

    public SendMessage processUsersInput(BotState botState, Message message) {
        long userId = message.getFrom().getId();
        String chatId = message.getChatId().toString();

        if (botState.equals(BotState.SEARCH_FAVORITES)) {
            personRequest = requestServer.getProfile(userId);

            PersonResponse favorite = requestServer.getCandidateFavorites(userId, START_PAGE);

            reLoadPersonRequest(favorite);

            SendPhoto sendPhoto = getSendPhoto(userId, favorite);
            tinderBot.getPhoto(sendPhoto);
            return null;
        }

        if (botState.equals(BotState.SEARCH_LIKE)) {

            //Сделайть сейв на серваке и ждем в ответ статус(Взаимно) и отправляем его в чат
            String statusFavorite  =  requestServer.saveLikeFavorites(userId, personRequest.getCurrentPersonId());
            if(FavoriteStatus.RECIPROCITY.getTranslate().equals(statusFavorite)){
                SendMessage sendMessage = new SendMessage(chatId,statusFavorite);
                //Кидаем сообщение если Взаимная лаф
                tinderBot.getMessage(sendMessage);
            }

            //получаем следующуую анкету...
            PersonResponse favorite = getNextPageAfterLike(userId);
            reLoadPersonRequest(favorite);
            //создаем и шлем фотку
            SendPhoto sendPhoto = getSendPhoto(userId, favorite);
            tinderBot.getPhoto(sendPhoto);

            return null;
        }

        if (botState.equals(BotState.SEARCH_DONT_LIKE)) {
            personRequest.setCurrentPage(getNextPage(personRequest.getTotalPage(), personRequest.getCurrentPage()));

            PersonResponse favorite = requestServer.getCandidateFavorites(userId,personRequest.getCurrentPage());

            reLoadPersonRequest(favorite);

            SendPhoto sendPhoto = getSendPhoto(userId, favorite);

            tinderBot.getPhoto(sendPhoto);
            return null;
        }
        return null;
    }

    private PersonResponse getNextPageAfterLike(Long userId) {
        if(personRequest.getTotalPage()-TWO_POSITION> personRequest.getCurrentPage()){ // проверяем будет ли запись с номером текущей записи в списке.
            return requestServer.getCandidateFavorites(userId,personRequest.getCurrentPage());
        } else if (personRequest.getTotalPage()-ONE_POSITION > 0){ // проверяем есть ли хоть какие то записи. е
            return requestServer.getCandidateFavorites(userId,START_PAGE);
        }else {
            throw new RuntimeException("Кончились кандидаты(");
        }
    }

    private void reLoadPersonRequest(PersonResponse favorite) {
        personRequest.setCurrentPage(favorite.getCurrentPage());
        personRequest.setTotalPage(favorite.getTotalPage());
        personRequest.setCurrentPersonId(favorite.getId());
    }


    private SendPhoto getSendPhoto(Long userId, PersonResponse favorite) {
        InputStream inputStream = new ByteArrayInputStream(favorite.getPicture());
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(inputStream, "picture.jpg"));
        sendPhoto.setChatId(String.valueOf(userId));
        sendPhoto.setCaption(favorite.getCaption());
        sendPhoto.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
        return sendPhoto;
    }

    private int getNextPage(int totalPage, int currentPage) {
        if(totalPage-ONE_POSITION>currentPage){
            return currentPage+ONE_POSITION;
        }
        return START_PAGE;
    }

}
