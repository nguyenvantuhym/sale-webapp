package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Service {
    public int serviceId;
    public String type;
}
