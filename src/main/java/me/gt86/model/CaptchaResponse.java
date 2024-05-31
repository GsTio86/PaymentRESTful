package me.gt86.model;

import lombok.Data;

@Data
public class CaptchaResponse {
    private boolean success;
    private String challenge_ts;
    private String hostname;
    private String action;
}