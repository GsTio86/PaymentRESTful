package me.gt86.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private String uid;
    private String time;
    private String uuid;
    private int type;
    private int money;
    private int server;
    private int payment;
    private int give;
    private String ip;
}
