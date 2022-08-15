package ru.liga.prerevolutionarytinderclient.bot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.InlineKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;

@Component
@RequiredArgsConstructor
public class CallbackQueryHandler {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final UserDataCache userDataCache;
    private final PersonRequest personRequest;
    private final RequestServer requestServer;


    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        long userId = buttonQuery.getFrom().getId();

        switch (data) {
            case "SIR":
            case "MADAM":
                personRequest.setGender(data.equals("SIR") ? "Сударъ" : "Сударыня");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_NAME);
                SendMessage sendMessage = new SendMessage(chatId, "Как вас величать?");
                sendMessage.enableMarkdown(true);
                return sendMessage;
            case "LADIES":
            case "GENTLEMEN":
            case "ALL":
                personRequest.setPreference(data.equals("LADIES") ? "Сударыни" : (data.equals("GENTLEMEN") ? "Судари" : "Все"));
                requestServer.creatProfile(personRequest);
                SendMessage sendMessage1 = new SendMessage(chatId, requestServer.getProfile(userId).toString());
                sendMessage1.enableMarkdown(true);
                sendMessage1.setReplyMarkup(replyKeyboardMaker.getProfileKeyboard());
                userDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
                return sendMessage1;
        }
        return null;
    }
}
