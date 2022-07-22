package ru.liga.prerevolutionarytinderclient.bot.handlers.fillingProfile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;

@Slf4j
@Component
public class FillingProfileHandler {

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



        if (botState.equals(BotState.FILLING_PROFILE)) {
            personRequest.setId(userId);
            SendMessage sendMessage = new SendMessage(chatId, "Анкету запили,ага\n ты кто?");
            sendMessage.setReplyMarkup(replyKeyboardMaker.getGenderKeyboard());
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_NAME);
            return sendMessage;
        }

        if (botState.equals(BotState.ASK_NAME)) {
            personRequest.setGender(inputText);
            SendMessage sendMessage = new SendMessage(chatId, "Как вас величать?");
            sendMessage.enableMarkdown(true);
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_DESK);
            return sendMessage;
        }

        if (botState.equals(BotState.ASK_DESK)) {
            personRequest.setName(inputText);
            SendMessage sendMessage = new SendMessage(chatId, "Раскажите о себе");
            sendMessage.enableMarkdown(true);
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_PREFERENCE);
            return sendMessage;
        }

        if (botState.equals(BotState.ASK_PREFERENCE)) {
            personRequest.setDescription(inputText);
            SendMessage sendMessage = new SendMessage(chatId, "Кого предпочитаете?");
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchGenderKeyboard());
            dataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
            return sendMessage;
        }

        if (botState.equals(BotState.PROFILE_FILLED)) {
            personRequest.setPreference(inputText);
            requestServer.creatProfile(personRequest);
            SendMessage sendMessage = new SendMessage(chatId, requestServer.getProfile(userId).toString());
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_DESK);
            return sendMessage;
        }
        return null;
    }
}
