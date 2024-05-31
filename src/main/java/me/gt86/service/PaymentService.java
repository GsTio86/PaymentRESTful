package me.gt86.service;

import me.gt86.model.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    default List<String> getReferer() {
        return List.of();
    }

    String payment(Payment payment);

}
