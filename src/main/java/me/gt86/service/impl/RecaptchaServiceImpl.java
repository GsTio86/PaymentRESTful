package me.gt86.service.impl;

import me.gt86.dao.impl.RecaptchaDaoImpl;
import me.gt86.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    @Autowired
    private RecaptchaDaoImpl rtd;

    @Override
    public boolean verifyRecaptcha(String recaptchaToken, String remoteIp) {
        return rtd.verifyRecaptcha(recaptchaToken, remoteIp);
    }
}
