package me.gt86.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.gt86.model.Payment;
import me.gt86.service.impl.FunPointServiceImpl;
import me.gt86.service.impl.RecaptchaServiceImpl;
import me.gt86.service.impl.SmseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private FunPointServiceImpl funPointServiceImpl;

    @Autowired
    private SmseServiceImpl smseServiceImpl;

    @Autowired
    private RecaptchaServiceImpl recaptchaServiceImpl;

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody Payment payment, HttpServletRequest request) {
        String remoteIp = request.getRemoteAddr();
        boolean isRecaptchaVerified = recaptchaServiceImpl.verifyRecaptcha(payment.getToken(), remoteIp);
        if (!isRecaptchaVerified) {
            return ResponseEntity.badRequest().body("無效的機器人驗證!");
        }
        try {
            String paymentHtml;
            switch (payment.getType().toUpperCase()) {
                case "ATM":
                case "CREDIT":
                    paymentHtml = funPointServiceImpl.payment(payment);
                    break;
                case "IBON":
                    paymentHtml = smseServiceImpl.payment(payment);
                    break;
                default:
                    return ResponseEntity.badRequest().body("無效的付款類型!");
            }

            return ResponseEntity.ok(paymentHtml);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
