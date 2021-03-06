package com.youcanPay.utils;

import java.util.HashMap;
import java.util.Map;

public class YCPayStrings {
    private static final Map<String, String> english = new HashMap<String, String>() {{
        put("unexpected_error_occurred", "An error occurred while processing the payment.");
        put("payment_canceled", "Payment canceled.");
        put("payment_with_card_invalid", "Payment with Credit Card is Invalid for your account.");
        put("payment_with_cashplus_invalid", "Payment with CashPlus is Invalid for your account.");
    }};

    private static final Map<String, String> french = new HashMap<String, String>() {{
        put("unexpected_error_occurred", "Une erreur s’est produite lors du traitement du paiement.");
        put("payment_canceled", "Paiement annulé.");
        put("payment_with_card_invalid", "Le paiement par carte de crédit n'est pas valide pour votre compte.");
        put("payment_with_cashplus_invalid", "Le paiement avec CashPlus n'est pas valide pour votre compte.");
    }};

    private static final Map<String, String> arabic = new HashMap<String, String>() {{
        put("unexpected_error_occurred", "حدث خطأ أثناء الدفع.");
        put("payment_canceled", "تم إلغاء الدفع.");
        put("payment_with_card_invalid", "الدفع ببطاقة الائتمان غير مفعل لحسابك.");
        put("payment_with_cashplus_invalid", "الدفع باستخدام CashPlus غير مفعل لحسابك.");
    }};

    public static String get(String id, String local) {
        if (local.equals("fr")) {
            return french.get(id);
        }

        if (local.equals("ar")) {
            return arabic.get(id);
        }

        return english.get(id);
    }
}
