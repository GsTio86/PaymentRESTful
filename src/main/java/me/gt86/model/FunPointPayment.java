package me.gt86.model;

import lombok.Data;

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
}