package ru.liga.prerevolutionarytinderclient.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TGBotConfig {

    @Value("${telegram.webhook-path}")
    private String webhookPath;

    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String botToken;
}
