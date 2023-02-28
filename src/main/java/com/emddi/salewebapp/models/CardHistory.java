package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class CardHistory {
    private int id;
    private int cardId;
    private int status;
    private int deviceId;
    private String fullName;
    private String scanTime;
}
