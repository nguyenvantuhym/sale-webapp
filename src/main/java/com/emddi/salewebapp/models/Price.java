package com.emddi.salewebapp.models;

import com.emddi.salewebapp.util.Number;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


@Getter
@Setter
@Builder(toBuilder = true)
public class Price {
    private int priceId;
    private int serviceId;
    private  String priceName;
    private int status;
    private int price;
    private int usdPrice;
    private int type;
    private int applyFor;
    private int vat;
    private int applyDiscount;
    private String invoiceInfo;
    private int sync;
    private String createdAt;
    private String updatedAt;


    public JSONObject toJson() {
        return new JSONObject(this);
    }

    public String toString() {
        return toJson().toString();
    }

    public String getDecimalPrice() {
        return Number.decimalFormat(price);
    }
}
