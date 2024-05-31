package me.gt86.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Utils {

    public static final String[] serversName = {"一號服", "二號服", "三號服", "四號服", "全伺服器"};

    public static final String[] paymentName = {"超商繳費", "PayPal", "信用卡付款", "超商代碼繳費", "超商條碼繳費", "ATM轉帳"};

    /*
     * 將沒有-的UUID轉換成UUID格式
     */
    public static String stringToUuid(String rawid) {
        if (rawid != null && rawid.length() == 32) {
            return rawid.substring(0, 8) + "-" +
                   rawid.substring(8, 12) + "-" +
                   rawid.substring(12, 16) + "-" +
                   rawid.substring(16, 20) + "-" +
                   rawid.substring(20);
        }
        return rawid;
    }

    /*
     * 產生現在時間
     */
    public static String getCurrentTime() {
        return getCurrentTime(new Date());
    }

    /*
     * 產生現在時間
     * @param date 現在時間
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String getCurrentTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        return sdf.format(date);
    }

    public static int between(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /*
     * 產生交易編號尾碼
     */
    public static String randomKey(boolean useLetters, int length) {
        String characters = useLetters ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "0123456789";
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            key.append(characters.charAt(random.nextInt(characters.length())));
        }
        return key.toString();
    }
}
