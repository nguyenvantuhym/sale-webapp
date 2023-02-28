package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.ArrayList;

@Getter
@Setter
@Builder(toBuilder = true)
public class FutureTicket {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String code;
    private Integer serviceId;
    private Integer adult;
    private Integer kid;
    private Integer totalPrice;
    private Integer discount;
    private Integer preMoney;
    private Integer resourcesId;
    private Integer typePay;
    private String checkinTime;
    private Integer status;
    private Integer userId;
    private Integer sync;
    private String createdAt;
    private String updatedAt;

    private ArrayList<FutureTicketValue> futureTicketValues;

    public JSONObject toJson() {
        return new JSONObject(this);
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public void setFutureTicketValues(ArrayList<FutureTicketValue> futureTicketValues) {
        this.futureTicketValues = futureTicketValues;
    }

}
