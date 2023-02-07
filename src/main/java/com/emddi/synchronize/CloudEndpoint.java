package com.emddi.synchronize;

import com.emddi.constants.StaticConfig;
import okhttp3.HttpUrl;

public class CloudEndpoint {

    public HttpUrl.Builder getBaseUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme("https").host(StaticConfig.apiHost).port(StaticConfig.apiPort)
                .addPathSegment("api").addPathSegment("hook");
    }
    public HttpUrl.Builder getDataSyncUrlBuilder() {
        return getBaseUrlBuilder().addPathSegment("data-sync");
    }

    public HttpUrl.Builder insertBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("insert");
    }
    public HttpUrl.Builder updateBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("update");
    }
    public HttpUrl.Builder syncInsertBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("sync-insert");
    }

    public HttpUrl.Builder syncInsertDownBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("sync-insert");
    }

    public HttpUrl.Builder syncUpdateDownBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("sync-update");
    }

    public HttpUrl.Builder syncPriceBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("future");
    }

    public HttpUrl.Builder syncRepUpdateDownBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("rep-update");
    }

    public HttpUrl.Builder syncRepInsertDownBuilder() {
        HttpUrl.Builder urlBuilder = getDataSyncUrlBuilder();
        return urlBuilder.addPathSegment("rep-insert");
    }

    public HttpUrl.Builder getTicketUrlBuilder() {
        return getBaseUrlBuilder().addPathSegment("ticket");
    }

    public HttpUrl.Builder getScanTicketUrlBuilder() {
        return getTicketUrlBuilder().addPathSegment("scan");
    }

    public HttpUrl.Builder getCheckTicketUrlBuilder() {
        return getTicketUrlBuilder().addPathSegment("check");
    }

}
