package ru.liga.prerevolutionarytinderclient.bot.handlers.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;
import ru.liga.prerevolutionarytinderclient.bot.cache.DataCache;
import ru.liga.prerevolutionarytinderclient.bot.cache.UserDataCache;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CommandHandler;
import ru.liga.prerevolutionarytinderclient.bot.keyboards.ReplyKeyboardMaker;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;
import ru.liga.prerevolutionarytinderclient.servicies.RequestServer;
import ru.liga.prerevolutionarytinderclient.types.BotState;
import ru.liga.prerevolutionarytinderclient.utils.MessageTemplate;

@Component
public class ShowProfileHandler implements CommandHandler {

    @Autowired
    ReplyKeyboardMaker replyKeyboardMaker;

    @Autowired
    PersonRequest personRequest;

    @Autowired
    RequestServer requestServer;

    @Autowired
    DataCache dataCache;

    @Autowired
    @Lazy
    TinderBot tinderBot;

    @Autowired
    UserDataCache userDataCache;

    @Autowired
    MessageTemplate messageTemplate;


    @Override
    public String getName() {
        return "Моя анкета";
    }

    @Override
    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {

        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_USER_PROFILE);
        PersonResponse profileUser = requestServer.getMyProfileForm(userId);
        tinderBot.getPhoto(messageTemplate.createMessagePhoto(userId, profileUser, replyKeyboardMaker.getProfileKeyboard()));

        return null;
    }
}