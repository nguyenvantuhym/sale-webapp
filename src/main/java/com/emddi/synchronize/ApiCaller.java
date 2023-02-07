package com.emddi.synchronize;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class ApiCaller {
    public static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();
}
