package me.gt86.dao.impl;

import me.gt86.dao.OrderDao;
import me.gt86.model.Order;
import me.gt86.service.impl.DiscordServiceImpl;
import me.gt86.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

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
            uuid = Utils.stringToUuid(uuid);
            int affectedRows = jdbcTemplate.update(sql, uid, time, uuid, type, money, server, payment, give, ip);
            if (affectedRows > 0) {
                String paymentName = Utils.paymentName[type];
                logger.info("資料庫新增新訂單: 編號 [{}] UUID [{}] 付款方式 [{}] 金額 [${}]", uid, uuid, paymentName, money);
                String message = String.format("[%s] UUID: %s 付款方式:%s 金額: %d", uid, uuid, paymentName, money);
                discord.sendDiscordNotificationAsync(message);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryUpdateOrder(String tradeUID) {
        String sql = "UPDATE " + table + " SET payment = 1 WHERE uid = ?";
        int affectedRows = jdbcTemplate.update(sql, tradeUID);
        if (affectedRows > 0) {
            logger.info("資料庫更新訂單付款完成: 編號 [{}]", tradeUID);
            String message = String.format("[%s] 付款已完成!", tradeUID);
            discord.sendDiscordNotificationAsync(message);
            return true;
        } else {
            logger.info("資料庫訂單不存在: 編號 [{}]", tradeUID);
        }
        return false;
    }

    @Override
    public boolean checkToken(String token) {
        return this.token.equals(token);
    }
}
