package ru.liga.prerevolutionarytinderclient.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.fillingProfile.FillingProfileHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;

@Component
public class MessageHandler {

/*  на случай если понадобится такой тип кнопок
    @Autowired
    InlineKeyboardMaker inlineKeyboardMaker;*/

    @Autowired
    FillingProfileHandler fillingProfileHandler;

    @Autowired
    DataCache dataCache;

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    RequestServer requestServer;

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
            default:
                botState = dataCache.getUsersCurrentBotState(userId);
                break;
        }

        dataCache.setUsersCurrentBotState(userId, botState);

        if (isFillingProfileState(botState)){
            replyMessage = fillingProfileHandler.processUsersInput(botState, message);
        } else if (botState.equals(BotState.SHOW_USER_PROFILE)) {
            replyMessage = new SendMessage(chatId, requestServer.getProfile(userId).toString());
        } else {
            replyMessage = new SendMessage(chatId, "Воспользуйтесь главным меню");
            replyMessage.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
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
}
