package ru.liga.prerevolutionarytinderclient.bot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.favorits.FavoritesHandler;
import ru.liga.prerevolutionarytinderclient.bot.handlers.fillingProfile.FillingProfileHandler;
import ru.liga.prerevolutionarytinderclient.bot.handlers.search.SearchHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Component
public class MessageHandler {
    @Autowired
    FillingProfileHandler fillingProfileHandler;
    @Autowired
    FavoritesHandler favoritesHandler;
    @Autowired
    SearchHandler searchHandler;
    @Autowired
    DataCache dataCache;
    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    RequestServer requestServer;

    @Autowired
    @Lazy
    TinderBot tinderBot;

    public BotApiMethod<?> answerMessage(Message message) {
        String inputText = message.getText();
        String chatId = message.getChatId().toString();
        Long userId = message.getFrom().getId();

//        if (personRequest.getId()==null){
//            personRequest.setId(userId);
//        }

        BotState botState;
        SendMessage replyMessage = null;
        switch (inputText) {
            case "/start":
                botState = BotState.FILLING_PROFILE;
                break;
            case "Изменить":
                botState = BotState.FILLING_PROFILE;
                break;
            case "Моя анкета":
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "Меню":
                botState = BotState.SHOW_HELP_MENU;
                break;
            case "Любимцы":
                botState = BotState.SHOW_FAVORITES;
                break;
            case "<--Лево":
                botState = BotState.SHOW_FAVORITES_PREVIOUS;
                break;
            case "Право-->":
                botState = BotState.SHOW_FAVORITES_NEXT;
                break;
            case "Поиск":
                botState = BotState.SEARCH_FAVORITES;
                break;
            case "<-Лево":
                botState = BotState.SEARCH_DONT_LIKE;
                break;
            case "Право->":
                botState = BotState.SEARCH_LIKE;
                break;
            default:
                botState = dataCache.getUsersCurrentBotState(userId);
                break;
        }

        dataCache.setUsersCurrentBotState(userId, botState);

        if (isFillingProfileState(botState)) {
            replyMessage = fillingProfileHandler.processUsersInput(botState, message);
        } else if (botState.equals(BotState.SHOW_USER_PROFILE)) {
            PersonRequest personRequest = requestServer.getProfile(userId);

            replyMessage = new SendMessage(chatId, personRequest.toString());

            InputStream inputStream = new ByteArrayInputStream(personRequest.getPicture());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(inputStream, "picture.jpg"));
            sendPhoto.setChatId(String.valueOf(userId));
            sendPhoto.setCaption(personRequest.getGender() + ", " + personRequest.getName());

            tinderBot.getPhoto(sendPhoto);
        } else if (isFavoritesState(botState)) {

            replyMessage = favoritesHandler.processUsersInput(botState, message);

        } else if (isSearchState(botState)) {

            replyMessage = searchHandler.processUsersInput(botState, message);

        } else {
            replyMessage = new SendMessage(chatId, "Воспользуйтесь главным меню");
            replyMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        }

        return replyMessage;
    }

    private boolean isFillingProfileState(BotState currentState) {
        switch (currentState) {
            case FILLING_PROFILE:
            case ASK_NAME:
            case ASK_DESK:
            case ASK_PREFERENCE:
            case PROFILE_FILLED:
                return true;
            default:
                return false;
        }
    }

    private boolean isFavoritesState(BotState currentState) {
        switch (currentState) {
            case SHOW_FAVORITES:
            case SHOW_FAVORITES_NEXT:
            case SHOW_FAVORITES_PREVIOUS:
                return true;
            default:
                return false;
        }
    }

    private boolean isSearchState(BotState currentState) {
        switch (currentState) {
            case SEARCH_FAVORITES:
            case SEARCH_DONT_LIKE:
            case SEARCH_LIKE:
                return true;
            default:
                return false;
        }
    }
}
