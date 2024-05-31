package me.gt86.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

public class FunPointUtils {

    /*
     * 計算FunPoint檢查碼
     * @param tradeInfo 交易資訊
     * @param hashKey 金鑰
     * @param hashIV 向量
     */
    public static String generateCheckMacValue(Map<String, String> tradeInfo, String hashKey, String hashIV) {
        // 從參數中移除CheckMacValue
        tradeInfo.remove("CheckMacValue");

        // 步驟 1：按鍵字母順序對參數進行排序
        String sortedParams = tradeInfo.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));

        // 步驟 2：在開頭加上HashKey，並在結尾加上HashIV
        String rawData = "HashKey=" + hashKey + "&" + sortedParams + "&HashIV=" + hashIV;

        // 步驟 3：將整個字串進行URL編碼
        String encodedData = urlEncode(rawData);

        // 步驟 4：轉換為小寫
        encodedData = encodedData.toLowerCase();

        // 步驟 5：計算SHA-256雜湊值
        String checkMacValue = sha256(encodedData);

        // 步驟 6：轉換為大寫
        return checkMacValue.toUpperCase();
    }

    /*
     * 轉換URL編碼
     */
    private static String urlEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8")
                .replace("%21", "!")
                .replace("%28", "(")
                .replace("%29", ")")
                .replace("%2A", "*")
                .replace("%7E", "~")
                .replace("%20", "+");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding URL", e);
        }
    }

    /*
     * 計算SHA-256雜湊值
     */
    private static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
