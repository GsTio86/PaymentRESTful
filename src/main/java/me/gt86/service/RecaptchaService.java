package me.gt86.service;

public interface RecaptchaService {
    boolean verifyRecaptcha(String recaptchaToken, String remoteIp);
}
