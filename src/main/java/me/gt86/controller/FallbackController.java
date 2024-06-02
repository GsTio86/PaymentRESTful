package me.gt86.controller;

import me.gt86.model.FunPointPayment;
import me.gt86.model.SmsePayment;
import me.gt86.service.impl.FunPointServiceImpl;
import me.gt86.service.impl.OrderServiceImpl;
import me.gt86.service.impl.SmseServiceImpl;
import me.gt86.utils.SmseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @Autowired
    private OrderServiceImpl osi;

    @Autowired
    private FunPointServiceImpl fpi;

    @Autowired
    private SmseServiceImpl smi;

    @PostMapping("/payment_funpoint")
    public ResponseEntity<String> funPonitSuccess(@RequestParam Map<String, String> params, @RequestHeader("referer") String referer) {
        FunPointPayment request = new FunPointPayment(params);
        if (osi.checkToken(request.getCustomField1()) && request.getMerchantID().equals(fpi.getMerchantID())) {
            if (fpi.getReferer().contains(referer)) {
                if (request.getCheckMacValue().length() == 64) {
                    String merchantTradeNo = request.getMerchantTradeNo();
                    if (merchantTradeNo != null && !merchantTradeNo.isEmpty()) {
                        if (request.getRtnCode() == 1 || request.getSimulatePaid() == 1) {
                            boolean success = osi.tryUpdateOrder(merchantTradeNo);
                            if (success) {
                                return ResponseEntity.ok("1|OK");
                            }
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok("0|ErrorMessage");
    }

    @PostMapping("/payment_smse")
    public ResponseEntity<String> smseSuccess(@RequestParam Map<String, String> params, @RequestHeader("referer") String referer) {
        SmsePayment smsePayment = new SmsePayment(params);
        String dataId = smsePayment.getData_id();
        String purchamt = smsePayment.getPurchamt();
        String amount = smsePayment.getAmount();
        String smseid = smsePayment.getSmseid();
        String midSmilepay = smsePayment.getMid_smilepay();
        if (smi.getReferer().contains(referer)) {
            String calculatedCode = SmseUtils.calculateVerifyCode(smi.getVerifyCode(), amount, smseid);
            if (calculatedCode.equals(midSmilepay) && purchamt.equals(amount)) {
                boolean success = osi.tryUpdateOrder(dataId);
                if (success) {
                    return ResponseEntity.ok("<Roturlstatus>SmilePay_OK</Roturlstatus>");
                }
            }
        }
        return ResponseEntity.ok("<Roturlstatus>SmilePay_ERROR</Roturlstatus>");
    }
}
