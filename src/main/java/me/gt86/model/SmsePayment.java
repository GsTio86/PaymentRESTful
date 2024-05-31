package me.gt86.model;

import lombok.Data;

@Data
public class SmsePayment {
    private String Classif;
    private String Classif_sub;
    private String Od_sob;
    private String Data_id;
    private String Process_date;
    private String Process_time;
    private String Response_id;
    private String Auth_code;
    private String LastPan;
    private String Payment_no;
    private String Purchamt;
    private String Amount;
    private String Errdesc;
    private String Pur_name;
    private String Tel_number;
    private String Mobile_number;
    private String Address;
    private String Email;
    private String Invoice_num;
    private String Remark;
    private String Smseid;
    private String Foreign;
    private String Verify_number;
    private String Mid_smilepay;
}