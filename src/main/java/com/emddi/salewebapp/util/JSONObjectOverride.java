package com.emddi.salewebapp.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectOverride extends JSONObject {
    public static String INT = "int";
    public static String STR = "str";
    public Integer getInteger(String key, String dataType) throws JSONException {
        if (super.isNull(key))
            return null;
        else {
            return super.getInt(key);

        }

    }

    public String getString(String key) throws JSONException {
        if (super.isNull(key))
            return null;
        else {
            return super.getString(key);

        }

    }

}
