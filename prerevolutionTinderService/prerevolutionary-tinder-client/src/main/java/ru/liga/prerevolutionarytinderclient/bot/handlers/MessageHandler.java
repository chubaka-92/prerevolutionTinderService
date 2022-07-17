package ru.liga.prerevolutionarytinderclient.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.Buttons;

@Component
public class MessageHandler {

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

/*  на случай если понадобится такой тип кнопок
    @Autowired
    InlineKeyboardMaker inlineKeyboardMaker;*/

    @Autowired
    PersonRequest personRequest;

    @Autowired
    RequestServer requestServer;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String inputText = message.getText();
        Long clientId = message.getFrom().getId();

        if (personRequest.getId()==null){
            personRequest.setId(clientId);
        }
        if (inputText.equals("/start")) {
            return getStartCreateProfile(chatId);
        } else if (inputText.contains("/name")) {
            personRequest.setName(inputText.substring(5));
            return getDescriptionProfile(chatId);
        } else if (inputText.contains("/desc")) {
            personRequest.setDescription(inputText.substring(5));
            return getSearchGenderProfile(chatId);
        } else if (inputText.equals(Buttons.SIR.getButtonName())
                || inputText.equals(Buttons.MADAM.getButtonName())) {
            personRequest.setGender(inputText);
            return getNameProfile(chatId);
        } else if (inputText.equals(Buttons.ALL.getButtonName())
                || inputText.equals(Buttons.GENTLEMEN.getButtonName())
                || inputText.equals(Buttons.LADIES.getButtonName())) {
            personRequest.setPreference(inputText);
            requestServer.creatProfile(personRequest);
            return getProfile(chatId,clientId);
        }
        return null;
    }

    private SendMessage getStartCreateProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Анкету запили,ага\n ты кто?");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getGenderKeyboard());
        return sendMessage;
    }

    private SendMessage getDescriptionProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Раскажите о себе");
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getSearchGenderProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Кого предпочитаете?");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getSearchGenderKeyboard());
        return sendMessage;
    }


    private SendMessage getNameProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Как вас величать?");
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getProfile(String chatId, Long clientId) {
        SendMessage sendMessage = new SendMessage(chatId, requestServer.getProfile(clientId).toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
        return sendMessage;
    }


}
