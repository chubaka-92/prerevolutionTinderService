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
            int page = 0;
            if(personRequest.getCurrentPage() >= 0 ){
               page = personRequest.getCurrentPage();
            }
            PersonRequest favorite = requestServer.getFavorites(userId,page);
            personRequest.setCurrentPage(1);

            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            dataCache.setUsersCurrentBotState(userId, BotState.SHOW_FAVORITES_NEXT);
            return sendMessage;
        }

        if (botState.equals(BotState.SHOW_FAVORITES_NEXT)) {
            PersonRequest favorite = requestServer.getFavorites(userId,personRequest.getCurrentPage());
            personRequest.setCurrentPage(personRequest.getCurrentPage()+1);
            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            dataCache.setUsersCurrentBotState(userId, BotState.SHOW_FAVORITES_NEXT);
            return sendMessage;
        }

        if (botState.equals(BotState.SHOW_FAVORITES_PREVIOUS)) {
            PersonRequest favorite = requestServer.getFavorites(userId,personRequest.getCurrentPage());
            personRequest.setCurrentPage(personRequest.getCurrentPage()-1);
            SendMessage sendMessage = new SendMessage(chatId, favorite.toString());
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchKeyboard());
            dataCache.setUsersCurrentBotState(userId, BotState.SHOW_FAVORITES_PREVIOUS);
            return sendMessage;
        }
        return null;
    }
}
