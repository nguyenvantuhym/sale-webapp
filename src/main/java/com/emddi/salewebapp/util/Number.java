package com.emddi.salewebapp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Number {
    public static String decimalFormat(Integer number) {
        return decimalFormat((number.toString()));
    };
    public static String decimalFormat (String number) {
        if (number == null || number.length() == 0) {
            return "0";
        }
        if (number.charAt(0) == '-') {
            number = number.substring(1);
        }
        if (number.charAt(number.length() - 1) == '.') {
            number = number.substring(0, number.length() - 1);
        }

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(new BigDecimal(number));
    }
}
