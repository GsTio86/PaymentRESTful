package me.gt86.dao;

public interface RecaptchaDao {
    boolean verifyRecaptcha(String recaptchaToken, String remoteIp);
}
