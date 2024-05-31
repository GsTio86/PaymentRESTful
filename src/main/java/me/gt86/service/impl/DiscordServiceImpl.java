package me.gt86.service.impl;

import me.gt86.dao.impl.DiscordDaoImpl;
import me.gt86.service.DiscordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscordServiceImpl implements DiscordService {

    @Autowired
    private DiscordDaoImpl ddi;

    public void sendDiscordNotification(String message) {
        ddi.sendDiscordNotification(message);
    }

    public void sendDiscordNotificationAsync(String message) {
        ddi.sendDiscordNotificationAsync(message);
    }
}
