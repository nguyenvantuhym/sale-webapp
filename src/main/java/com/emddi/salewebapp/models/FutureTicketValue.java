package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class FutureTicketValue {
    private int futureTicketId;
    private int priceId;
    private String code;
    private int value;
    private int sync;
    private String createdAt;
    private String updatedAt;
}
