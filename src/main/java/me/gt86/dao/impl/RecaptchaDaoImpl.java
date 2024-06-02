package me.gt86.dao.impl;

import me.gt86.dao.RecaptchaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RecaptchaDaoImpl implements RecaptchaDao {

    @Value("${google.test_mode}")
    private boolean testMode;

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${google.test_secret}")
    private String recaptchaTestSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean verifyRecaptcha(String recaptchaToken, String remoteIp) {
        String url = "https://www.google.com/recaptcha/api/siteverify";

        String secret = testMode ? recaptchaTestSecret : recaptchaSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> params = new HashMap<>();
        params.put("secret", secret);
        params.put("response", recaptchaToken);
        params.put("remoteip", remoteIp);

        StringBuilder requestBody = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (requestBody.length() > 0) {
                requestBody.append("&");
            }
            requestBody.append(entry.getKey()).append("=").append(entry.getValue());
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        Map<String, Object> body = response.getBody();

        return body != null && (Boolean) body.get("success");
    }
}
