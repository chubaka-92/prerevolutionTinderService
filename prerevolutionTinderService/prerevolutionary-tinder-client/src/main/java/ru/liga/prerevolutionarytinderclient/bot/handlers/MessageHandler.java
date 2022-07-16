package ru.liga.prerevolutionarytinderclient.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.types.Buttons;

@Component
public class MessageHandler {

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;
    @Autowired
    InlineKeyboardMaker inlineKeyboardMaker;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (inputText.equals("/start")) {
            return getStartCreateProfile(chatId);
        }else if (inputText.contains("/name")) {
            return getDescriptionProfile(chatId);
        } else if (inputText.contains("/desc")) {
            return getSearchGenderProfile(chatId);
        }
        return null;
    }

    private SendMessage getStartCreateProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Анкету запили,ага\n ты кто?");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getGenderInlineMessageButtons());
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
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getSearchGenderInlineMessageButtons());
        return sendMessage;
    }


}
