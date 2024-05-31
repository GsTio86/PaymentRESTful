package me.gt86.service.impl;

import me.gt86.dao.impl.FunPointDaoImpl;
import me.gt86.model.Payment;
import me.gt86.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FunPointServiceImpl implements PaymentService {

    @Autowired
    private FunPointDaoImpl fpd;

    @Override
    public List<String> getReferer() {
        return fpd.getReferer();
    }

    @Override
    public String payment(Payment payment) {
        return fpd.payment(payment);
    }

    public String getMerchantID() {
        return fpd.getMerchantID();
    }
}