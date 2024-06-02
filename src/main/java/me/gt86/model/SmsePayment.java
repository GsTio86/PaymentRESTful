package me.gt86.model;

import lombok.Data;

import java.util.Map;

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

    public SmsePayment(Map<String, String> params) {
        this.Classif = params.get("Classif");
        this.Classif_sub = params.get("Classif_sub");
        this.Od_sob = params.get("Od_sob");
        this.Data_id = params.get("Data_id");
        this.Process_date = params.get("Process_date");
        this.Process_time = params.get("Process_time");
        this.Response_id = params.get("Response_id");
        this.Auth_code = params.get("Auth_code");
        this.LastPan = params.get("LastPan");
        this.Payment_no = params.get("Payment_no");
        this.Purchamt = params.get("Purchamt");
        this.Amount = params.get("Amount");
        this.Errdesc = params.get("Errdesc");
        this.Pur_name = params.get("Pur_name");
        this.Tel_number = params.get("Tel_number");
        this.Mobile_number = params.get("Mobile_number");
        this.Address = params.get("Address");
        this.Email = params.get("Email");
        this.Invoice_num = params.get("Invoice_num");
        this.Remark = params.get("Remark");
        this.Smseid = params.get("Smseid");
        this.Foreign = params.get("Foreign");
        this.Verify_number = params.get("Verify_number");
        this.Mid_smilepay = params.get("Mid_smilepay");
    }
}