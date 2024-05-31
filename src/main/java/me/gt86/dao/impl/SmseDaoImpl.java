package me.gt86.dao.impl;

import me.gt86.dao.PaymentDao;
import me.gt86.model.Payment;
import me.gt86.service.impl.OrderServiceImpl;
import me.gt86.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SmseDaoImpl implements PaymentDao {

    @Value("${smse.dcvc}")
    private String smseDcvc;

    @Value("${smse.rvg2c}")
    private String smseRvg2c;

    @Value("${smse.url}")
    private String smseUrl;

    @Value("${smse.fallback}")
    private String smseFallback;

    @Value("${smse.test.mode}")
    private boolean debug;

    @Value("${smse.test.url}")
    private String smseTestUrl;

    @Value("${smse.verifyCode}")
    private String verifyCode;

    @Autowired
    private OrderServiceImpl osi;

    @Override
    public List<String> getReferer() {
        return List.of(
            "http://ssl.smse.com.tw",
            "https://ssl.smse.com.tw",
            "http://ssl.smse.com.tw/",
            "https://ssl.smse.com.tw/"
        );
    }

    @Override
    public String generateTradeUID() {
        int num = Utils.between(1000000, 3999999) + Utils.between(1000, 9000);
        String prefix = debug ? "TEST" : "MCS";
        return prefix + num + Utils.randomKey(false, 6);
    }

    @Override
    public String generatePaymentForm(Map<String, String> tradeInfo, String url) {
        StringBuilder html = new StringBuilder();
        String formId = "_auto_smse_Form_" + new Date().getTime();
        html.append("<form id=\"").append(formId).append("\" method=\"post\" action=\"").append(url).append("\">");
        tradeInfo.forEach((key, value) -> html.append("<input type=\"hidden\" name=\"").append(key).append("\" value=\"").append(value).append("\">"));
        html.append("</form>");
        html.append("<script>document.getElementById(\"").append(formId).append("\").submit();</script>");
        return html.toString();
    }

    @Override
    public String payment(Payment payment) {
        String tradeUID = generateTradeUID();
        String time = Utils.getCurrentTime();

        boolean success = osi.tryCreateOrder(tradeUID, time, payment.getRawid(), 4, payment.getAmount(), 5,"");
        if (!success) {
            throw new RuntimeException("建立訂單失敗!");
        }
        Map<String, String> tradeInfo = new HashMap<>();
        tradeInfo.put("Dcvc", smseDcvc);
        tradeInfo.put("Rvg2c", smseRvg2c);
        tradeInfo.put("Od_sob", Utils.serversName[4] + " - $" + payment.getAmount());
        tradeInfo.put("Pay_zg", "4");
        tradeInfo.put("Data_id", tradeUID);
        tradeInfo.put("Amount", String.valueOf(payment.getAmount()));
        tradeInfo.put("Roturl", smseFallback);
        tradeInfo.put("Remark", "遊戲ID:" + payment.getUsername() + " UUID:" + payment.getRawid());

        return generatePaymentForm(tradeInfo, debug ? smseTestUrl : smseUrl);
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
