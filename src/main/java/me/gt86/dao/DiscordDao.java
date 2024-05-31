package me.gt86.dao;

public interface DiscordDao {

    void sendDiscordNotification(String message);

    void sendDiscordNotificationAsync(String message);
}
