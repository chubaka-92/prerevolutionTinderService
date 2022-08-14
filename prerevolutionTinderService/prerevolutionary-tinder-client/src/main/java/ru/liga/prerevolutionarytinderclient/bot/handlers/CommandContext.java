package ru.liga.prerevolutionarytinderclient.bot.handlers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class CommandContext {
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    public CommandContext(List<CommandHandler> commandHandlersList) {
        commandHandlersList.forEach(handler -> commandHandlers.put(handler.getName(), handler));
    }

    public BotApiMethod<?> handle(long userId, String chatId, String inputText) {
        return getCommandHandler(inputText).handle(userId, chatId, inputText);
    }

    private CommandHandler getCommandHandler(String inputText) {
        return commandHandlers.getOrDefault(inputText, commandHandlers.get("Default"));
    }
}