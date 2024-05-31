package me.gt86.service;

public interface DiscordService {

    void sendDiscordNotification(String message);

    void sendDiscordNotificationAsync(String message);
}
