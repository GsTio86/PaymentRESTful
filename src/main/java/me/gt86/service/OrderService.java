package me.gt86.service;

public interface OrderService {
    boolean tryCreateOrder(String uid, String time, String uuid, int type, int money, int server, String ip);

    boolean tryUpdateOrder(String tradeUID);

    boolean checkToken(String token);
}
