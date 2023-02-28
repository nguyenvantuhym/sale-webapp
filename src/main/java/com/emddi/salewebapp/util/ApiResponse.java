package com.emddi.salewebapp.util;


import org.json.JSONObject;

public class ApiResponse {
    Boolean success = false;
    String message = "";

    Data data;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public <T> void setData(T data) {
        this.data = new Data<>(data);
    }

    @Override
    public String toString() {
        JSONObject res = new JSONObject();
        res.put("success", success);
        res.put("message", message);
        if (this.data != null) res.put("data", this.data.toString());
        return res.toString();
    }


    static class Data<T> {
        private final T data;

        Data(T data) {
            this.data = data;
        }

        public String toString() {
            return data.toString();
        }
    }
}
