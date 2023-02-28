package com.emddi.salewebapp.util;

import org.json.JSONObject;

public class Sanitizer {
    public static String replaceNullWithJSONObjectNull(String input) {
        if (input == null) {
            return JSONObject.NULL.toString();
        }
        return input;
    }
}
