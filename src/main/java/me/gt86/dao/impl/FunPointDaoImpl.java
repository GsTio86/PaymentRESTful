package me.gt86.dao.impl;

import me.gt86.dao.PaymentDao;
import me.gt86.model.Payment;
import me.gt86.service.impl.OrderServiceImpl;
import me.gt86.utils.FunPointUtils;
import me.gt86.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FunPointDaoImpl implements PaymentDao {

    @Value("${funpoint.merchantId}")
    private String merchantID;

    @Value("${funpoint.hashKey}")
    private String hashKey;

    @Value("${funpoint.hashIV}")
    private String hashIV;

    @Value("${funpoint.url}")
    private String url;

    @Value("${funpoint.fallback}")
    private String fallbackUrl;

    @Value("${funpoint.test.mode}")
    private boolean debug;

    @Value("${funpoint.test.url}")
    private String testUrl;

    @Value("${funpoint.test.merchantId}")
    private String testMerchantID;

    @Value("${funpoint.test.hashKey}")
    private String testHashKey;

    @Value("${funpoint.test.hashIV}")
    private String testHashIV;

    @Value("${funpoint.description}")
    private String description;

    @Value("${safety.token}")
    private String token;

    @Autowired
    private OrderServiceImpl osi;

    @Override
    public List<String> getReferer() {
        return List.of(
            "http://funpoint.com.tw",
            "https://funpoint.com.tw",
            "http://funpoint.com.tw/",
            "https://funpoint.com.tw/"
        );
    }

    @Override
    public String generateTradeUID(String paymentTypeId) {
        int num = Utils.between(1000000, 3999999) + Utils.between(1000, 9000);
        String prefix = debug ? "TEST" : "";
        if (paymentTypeId.contains("ATM")) {
            prefix += "MCA";
        } else if (paymentTypeId.contains("Credit")) {
            prefix += "MCP";
        }
        return prefix + num + Utils.randomKey(false, 6);
    }

    @Override
    public String generatePaymentForm(Map<String, String> tradeInfo, String url) {
        StringBuilder html = new StringBuilder();
        String sha256 = FunPointUtils.generateCheckMacValue(tradeInfo, debug ? testHashKey : hashKey, debug ? testHashIV : hashIV);
        String formId = "_auto_funpoint_Form_" + new Date().getTime();
        html.append("<form id=\"").append(formId).append("\" method=\"post\" action=\"").append(url).append("\">");
        tradeInfo.forEach((key, value) -> html.append("<input type=\"hidden\" name=\"").append(key).append("\" value=\"").append(value).append("\">"));
        html.append("<input type=\"hidden\" name=\"CheckMacValue\" value=\"").append(sha256).append("\">");
        html.append("</form>");
        html.append("<script>document.getElementById(\"").append(formId).append("\").submit();</script>");
        return html.toString();
    }

    private String createPayment(Payment payment, String paymentType, int paymentTypeId) {
        String tradeUID = generateTradeUID(paymentType);
        String time = Utils.getCurrentTime();
        boolean success = osi.tryCreateOrder(tradeUID, time, payment.getRawid(), paymentTypeId, payment.getAmount(), 5,"");
        if (!success) {
            throw new RuntimeException("建立訂單失敗!");
        }
        Map<String, String> tradeInfo = new HashMap<>();
        tradeInfo.put("ExpireDate", String.valueOf(7));
        tradeInfo.put("CheckMacValue", "");
        tradeInfo.put("ChoosePayment", paymentType);
        tradeInfo.put("EncryptType", "1");
        tradeInfo.put("ItemName", Utils.serversName[4] + " - $" + payment.getAmount());
        tradeInfo.put("MerchantID", merchantID);
        tradeInfo.put("MerchantTradeDate", time);
        tradeInfo.put("MerchantTradeNo", tradeUID);
        tradeInfo.put("PaymentType", "aio");
        tradeInfo.put("ReturnURL", fallbackUrl);
        tradeInfo.put("TotalAmount", String.valueOf(payment.getAmount()));
        tradeInfo.put("TradeDesc", description);
        tradeInfo.put("CustomField1", token);
        tradeInfo.put("CustomField2", payment.getUsername());
        tradeInfo.put("CustomField3", payment.getRawid());

        return generatePaymentForm(tradeInfo, debug ? testUrl : url);
    }

    @Override
    public String payment(Payment payment) {
        String type = payment.getType();
        if (type.equalsIgnoreCase("ATM")) {
            return createPayment(payment, "ATM", 6);
        } else if (type.equalsIgnoreCase("Credit")) {
            return createPayment(payment, "Credit", 2);
        } else {
            throw new RuntimeException("無效的付款類型!");
        }
    }

    public String getMerchantID() {
        return merchantID;
    }

}
