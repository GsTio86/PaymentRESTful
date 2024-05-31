package me.gt86.service.impl;

import me.gt86.dao.impl.SmseDaoImpl;
import me.gt86.model.Payment;
import me.gt86.service.PaymentService;
import me.gt86.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmseServiceImpl implements PaymentService {

    @Autowired
    private SmseDaoImpl smd;

    @Override
    public List<String> getReferer() {
        return smd.getReferer();
    }

    @Override
    public String payment(Payment payment) {
        return smd.payment(payment);
    }

    public String getVerifyCode() {
        return smd.getVerifyCode();
    }
}