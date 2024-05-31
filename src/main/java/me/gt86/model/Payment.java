package me.gt86.model;

import lombok.Data;

@Data
public class Payment {
    private String type;
    private String username;
    private String rawid;
    private Integer amount;
}