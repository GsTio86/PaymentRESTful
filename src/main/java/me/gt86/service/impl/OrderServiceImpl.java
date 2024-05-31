package me.gt86.service.impl;

import me.gt86.dao.impl.OrderDaoImpl;
import me.gt86.model.Order;
import me.gt86.service.OrderService;
import me.gt86.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDaoImpl odi;

    @Override
    public boolean tryCreateOrder(String uid, String time, String uuid, int type, int money, int server, String ip) {
       return odi.tryCreateOrder(Order.builder()
                .uid(uid)
                .time(time)
                .uuid(uuid)
                .type(type)
                .money(money)
                .server(server)
                .payment(0)
                .give(0)
                .ip(ip)
                .build());
    }

    @Override
    public boolean tryUpdateOrder(String uid) {
        return odi.tryUpdateOrder(uid);
    }

    @Override
    public boolean checkToken(String token) {
        return odi.checkToken(token);
    }
}
