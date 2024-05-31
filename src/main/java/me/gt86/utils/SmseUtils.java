package me.gt86.utils;

public class SmseUtils {

    /*
     * 計算速買配驗證碼
     */
    public static String calculateVerifyCode(String verifyCode, String amount, String smseid) {
        // Step 1: A = VerifyCode padded to 4 digits
        String A = String.format("%04d", Integer.parseInt(verifyCode));

        // Step 2: B = Amount padded to 8 digits
        String B = String.format("%08d", Integer.parseInt(amount));

        // Step 3: C = last 4 digits of Smseid, replacing non-digits with '9', padded to 4 digits
        String smseidLastFour = smseid.length() >= 4 ? smseid.substring(smseid.length() - 4) : smseid;
        smseidLastFour = smseidLastFour.replaceAll("\\D", "9");
        String C = String.format("%04d", Integer.parseInt(smseidLastFour));

        // Step 4: D = concatenation of A, B, and C
        String D = A + B + C;

        // Step 5: sumEven = sum of digits at even positions (0-based index)
        int sumEven = 0;
        for (int i = 1; i < D.length(); i += 2) {
            sumEven += Character.getNumericValue(D.charAt(i));
        }
        int E = sumEven * 3;

        // Step 6: sumOdd = sum of digits at odd positions (0-based index)
        int sumOdd = 0;
        for (int i = 0; i < D.length(); i += 2) {
            sumOdd += Character.getNumericValue(D.charAt(i));
        }
        int F = sumOdd * 9;

        // Step 7: return E + F
        return String.valueOf(E + F);
    }
}
