package me.gt86.dao;

import me.gt86.model.Order;

public interface OrderDao {

    boolean tryCreateOrder(Order order);

    boolean tryUpdateOrder(String tradeUID);

    boolean checkToken(String token);
}
