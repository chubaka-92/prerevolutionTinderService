package ru.liga.prerevolutionarytinderclient.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.types.Buttons;

@Component
public class CallbackQueryHandler {

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;
    @Autowired
    InlineKeyboardMaker inlineKeyboardMaker;


    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery){
        final String chatId = buttonQuery.getMessage().getChatId().toString();

        String data = buttonQuery.getData();

        if (data.equals(Buttons.SIR.name()) || data.equals(Buttons.MADAM.name())) {
            return getNameProfile(chatId);
        }else if (data.equals(Buttons.ALL.name())
                || data.equals(Buttons.GENTLEMEN.name())
                || data.equals(Buttons.LADIES.name())) {
            return getProfile(chatId);
        }
        return null;
    }

    private SendMessage getNameProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Как вас величать?");
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getProfile(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Тут должна быть твоя анкета, но я ее не сделал)");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
        return sendMessage;
    }

}
