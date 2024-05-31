package me.gt86.dao;

import me.gt86.model.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentDao {
    default List<String> getReferer() {
        return List.of();
    }

    /*
     * 產生訂單編號
     */
    default String generateTradeUID() {
        return "";
    }

    default String generateTradeUID(String type) {
        return type + generateTradeUID();
    }

    /*
     * 產生付款表單
     * @param tradeInfo 交易資訊
     * @param url 金流API網址
     */
    String generatePaymentForm(Map<String, String> tradeInfo, String url);

    /*
     * 付款
     * @param payment 付款資訊
     */
    String payment(Payment payment);

}
