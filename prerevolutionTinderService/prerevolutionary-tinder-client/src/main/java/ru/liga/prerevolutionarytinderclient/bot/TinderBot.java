package ru.liga.prerevolutionarytinderclient.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CallbackQueryHandler;
import ru.liga.prerevolutionarytinderclient.bot.handlers.MessageHandler;

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

    public TinderBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
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
                    "Шото не так опять:" + e);
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null) {
            return messageHandler.answerMessage(update.getMessage());
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    /**
     * Метод реализует отправление картинки по запросу.
     *
     * @param sendPhoto сообщение любого типа.
     */
    @SneakyThrows
    public void getPhoto(SendPhoto sendPhoto) {
        log.info("Was calling getPhoto.");
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Ошибка: невозможно отправить файл." + e);
            throw new RuntimeException("Ошибка: невозможно отправить файл.");
        }
    }
}
