package ru.liga.prerevolutionarytinderclient.bot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.favorits.FavoritesHandler;
import ru.liga.prerevolutionarytinderclient.bot.handlers.fillingProfile.FillingProfileHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;

@Slf4j
@Component
public class MessageHandler {


    @Autowired
    FillingProfileHandler fillingProfileHandler;

    @Autowired
    FavoritesHandler favoritesHandler;

    @Autowired
    DataCache dataCache;

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    RequestServer requestServer;

/*    @Autowired
    @Lazy
    TinderBot tinderBot;*/


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

            //собираем фотку и пытаемся ее отправить..но чото..он ее не может кинуть
            // SendPhoto sendPhoto = new SendPhoto(chatId,requestServer.getProfileImage(userId));
            InputFile inputFile = new InputFile().setMedia(requestServer.getProfileImage2(userId), "image.jpg");
            SendPhoto sendPhoto = new SendPhoto(chatId, inputFile);
            sendPhoto.setCaption(personRequest.getGender() + ", " + personRequest.getName());
            sendPhoto.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
            //tinderBot.getPhoto(sendPhoto); //тут отправка раскоментить если хош попытать удачу в отправке фотки. ошибка Error sending photo: [404] Not Found

        } else if (isFavoritesState(botState)) {

            replyMessage = favoritesHandler.processUsersInput(botState, message);

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
}
