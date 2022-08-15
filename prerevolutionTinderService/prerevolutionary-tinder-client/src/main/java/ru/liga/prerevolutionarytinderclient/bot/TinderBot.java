package ru.liga.prerevolutionarytinderclient.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CallbackQueryHandler;
import ru.liga.prerevolutionarytinderclient.bot.handlers.MessageHandler;
import ru.liga.prerevolutionarytinderclient.config.TGBotConfig;

@Slf4j
@Getter
@Setter
@Component
public class TinderBot extends SpringWebhookBot {

    private String botPath;

    private String botName;

    private String botToken;

    MessageHandler messageHandler;
    CallbackQueryHandler callbackQueryHandler;

    public TinderBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler,
                     TGBotConfig tgBotConfig) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.botName = tgBotConfig.getBotName();
        this.botToken = tgBotConfig.getBotToken();
        this.botPath = tgBotConfig.getWebhookPath();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Шото не так" + e);
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Шото не так опять:" + e.getMessage());
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update.getMessage());
            }
        }
        return null;
    }

    /**
     * Метод реализует отправление картинки по запросу.
     *
     * @param sendPhoto сообщение любого типа.
     */
    public void getPhoto(SendPhoto sendPhoto) {
        log.info("Was calling getPhoto.");
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Ошибка: невозможно отправить файл." + e);
            throw new RuntimeException("Ошибка: невозможно отправить файл.");
        }
    }

    /**
     * Метод реализует отправление сообщеня по запросу.
     *
     * @param sendMessage сообщение любого типа.
     */
    public void getMessage(SendMessage sendMessage) {
        log.info("Was calling getPhoto.");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка: невозможно отправить сообщение." + e);
            throw new RuntimeException("Ошибка: невозможно отправить сообщение.");
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

}
