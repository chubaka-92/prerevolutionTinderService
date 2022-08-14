package ru.liga.prerevolutionarytinderclient.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
@Service
public class MessageTemplate {

    @Autowired
    private ReplyKeyboardMaker replyKeyboardMaker;

    public SendMessage createMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    public SendMessage createMessageAndReplyMarkup(String chatId, String message, ReplyKeyboard keyboard) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboard);
        return sendMessage;
    }

    public SendPhoto createMessagePhoto(Long userId, PersonResponse profileUser, ReplyKeyboardMarkup replyKeyboardMarkup) {
        InputStream inputStream = new ByteArrayInputStream(profileUser.getPicture());
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(inputStream, "picture.jpg"));
        sendPhoto.setChatId(String.valueOf(userId));
        sendPhoto.setCaption(profileUser.getCaption());
        sendPhoto.setReplyMarkup(replyKeyboardMarkup);
        return sendPhoto;
    }
}
