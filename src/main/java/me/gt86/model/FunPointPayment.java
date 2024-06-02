package me.gt86.model;

import lombok.Data;

import java.util.Map;

@Data
public class FunPointPayment {
    private String MerchantID;
    private String MerchantTradeNo;
    private String StoreID;
    private int RtnCode;
    private String RtnMsg;
    private String TradeNo;
    private int TradeAmt;
    private String PaymentDate;
    private String PaymentType;
    private int PaymentTypeChargeFee;
    private String TradeDate;
    private int SimulatePaid;
    private String CustomField1;
    private String CustomField2;
    private String CustomField3;
    private String CustomField4;
    private String CheckMacValue;

    public FunPointPayment(Map<String, String> params) {
        this.MerchantID = params.get("MerchantID");
        this.MerchantTradeNo = params.get("MerchantTradeNo");
        this.StoreID = params.get("StoreID");
        this.RtnCode = Integer.parseInt(params.get("RtnCode"));
        this.RtnMsg = params.get("RtnMsg");
        this.TradeNo = params.get("TradeNo");
        this.TradeAmt = Integer.parseInt(params.get("TradeAmt"));
        this.PaymentDate = params.get("PaymentDate");
        this.PaymentType = params.get("PaymentType");
        this.PaymentTypeChargeFee = Integer.parseInt(params.get("PaymentTypeChargeFee"));
        this.TradeDate = params.get("TradeDate");
        this.SimulatePaid = Integer.parseInt(params.get("SimulatePaid"));
        this.CustomField1 = params.get("CustomField1");
        this.CustomField2 = params.get("CustomField2");
        this.CustomField3 = params.get("CustomField3");
        this.CustomField4 = params.get("CustomField4");
        this.CheckMacValue = params.get("CheckMacValue");
    }
}