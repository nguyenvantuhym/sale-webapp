package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Card {
    private Integer cardId;
    private String cardNo;
    private Integer officeId;
    private String fullName;
    private String phoneNumber;
    private Integer status;
    private String zone;
    private Integer sync;
    private String createdAt;
}
