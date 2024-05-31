package me.gt86.dao.impl;

import me.gt86.dao.OrderDao;
import me.gt86.model.Order;
import me.gt86.service.impl.DiscordServiceImpl;
import me.gt86.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DiscordServiceImpl discord;

    @Value("${spring.datasource.table}")
    private String table;

    @Value("${safety.token}")
    private String token;

    @Override
    public boolean tryCreateOrder(Order order) {
        String uid = order.getUid();
        String time = order.getTime();
        String uuid = order.getUuid();
        int type = order.getType();
        int money = order.getMoney();
        int server = order.getServer();
        int payment = order.getPayment();
        int give = order.getGive();
        String ip = order.getIp();
        if (uuid != null && !uuid.isEmpty()) {
            String sql = "INSERT INTO " + table + " VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? )";
            int affectedRows = jdbcTemplate.update(sql, uid, time, Utils.stringToUuid(uuid), type, money, server, payment, give, ip);
            if (affectedRows > 0) {
                String currentTime = Utils.getCurrentTime();
                System.out.printf("[%s] New order added: %s%n", currentTime, uid);
                String message = String.format("[%s] UUID: %s 付款方式:%s 金額: %d", uid, Utils.stringToUuid(uuid), Utils.paymentName[payment], money);
                discord.sendDiscordNotificationAsync(message);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryUpdateOrder(String tradeUID) {
        String sql = "UPDATE orders SET payment = 1 WHERE uid = ?";
        int affectedRows = jdbcTemplate.update(sql, tradeUID);
        if (affectedRows > 0) {
            String currentTime = Utils.getCurrentTime();
            System.out.printf("[%s] Payment updated: %s%n", currentTime, tradeUID);

            String message = String.format("[%s] 付款已完成!", tradeUID);
            discord.sendDiscordNotificationAsync(message);
            return true;
        } else {
            System.out.printf("[%s] Payment not found: %s%n", Utils.getCurrentTime(), tradeUID);
        }
        return false;
    }

    @Override
    public boolean checkToken(String token) {
        return this.token.equals(token);
    }
}
