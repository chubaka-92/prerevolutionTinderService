package ru.liga.prerevolutionarytinderclient.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;
import ru.liga.prerevolutionarytinderclient.bot.handlers.CallbackQueryHandler;
import ru.liga.prerevolutionarytinderclient.bot.handlers.MessageHandler;


@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final TGBotConfig tgBotConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(tgBotConfig.getWebhookPath()).build();
    }

    @Bean
    public TinderBot springWebhookBot(SetWebhook setWebhook,
                                      MessageHandler messageHandler,
                                      CallbackQueryHandler callbackQueryHandler) {
        TinderBot bot = new TinderBot(setWebhook, messageHandler,callbackQueryHandler);

        bot.setBotPath(tgBotConfig.getWebhookPath());
        bot.setBotName(tgBotConfig.getBotName());
        bot.setBotToken(tgBotConfig.getBotToken());

        return bot;
    }
}
