package me.gt86.dao.impl;

import me.gt86.dao.RecaptchaDao;
import me.gt86.model.CaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class RecaptchaDaoImpl implements RecaptchaDao {

    @Value("${google.test_mode}")
    private boolean testMode;

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${google.recaptcha.test_secret}")
    private String recaptchaTestSecret;

    @Autowired
    private RestTemplate restTemplate;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";

    @Override
    public boolean verifyRecaptcha(String recaptchaToken, String remoteIp) {
        URI verifyUri = URI.create(String.format(RECAPTCHA_VERIFY_URL, testMode ? recaptchaTestSecret : recaptchaSecret, recaptchaToken, remoteIp));
        CaptchaResponse response = restTemplate.getForObject(verifyUri, CaptchaResponse.class);
        return response != null && response.isSuccess();
    }
}
