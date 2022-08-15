package ru.liga.prerevolutionarytinderclient.bot.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final CommandContext commandContext;

    public BotApiMethod<?> answerMessage(Message message) {
        String inputText = message.getText();
        String chatId = message.getChatId().toString();
        Long userId = message.getFrom().getId();
        return commandContext.handle(userId, chatId, inputText);
    }
}