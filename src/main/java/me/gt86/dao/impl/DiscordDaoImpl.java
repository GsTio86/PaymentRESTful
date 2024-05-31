package me.gt86.dao.impl;

import me.gt86.dao.DiscordDao;
import me.gt86.utils.DiscordWebhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class DiscordDaoImpl implements DiscordDao {
    @Value("${discord.username}")
    private String userName;

    @Value("${discord.webhook}")
    private String webhookURL;

    @Value("${discord.image}")
    private String imageUrl;


    @Override
    public void sendDiscordNotification(String message) {
        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
        webhook.setUsername(userName);
        webhook.setAvatarUrl(imageUrl);
        webhook.setContent(message);
        try {
            webhook.execute();
        } catch (Exception e) {}
    }

    @Override
    public void sendDiscordNotificationAsync(String message) {
        CompletableFuture.runAsync(() -> sendDiscordNotification(message));
    }
}
