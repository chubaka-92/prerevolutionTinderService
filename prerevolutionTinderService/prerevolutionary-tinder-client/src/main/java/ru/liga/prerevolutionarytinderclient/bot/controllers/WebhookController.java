package ru.liga.prerevolutionarytinderclient.bot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutionarytinderclient.bot.TinderBot;

@RestController
public class WebhookController {

    private final TinderBot tinderBot;

    public WebhookController(TinderBot tinderBot) {
        this.tinderBot = tinderBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return tinderBot.onWebhookUpdateReceived(update);
    }
}