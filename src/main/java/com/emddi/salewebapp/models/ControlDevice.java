package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class ControlDevice {
    private int id;
    private String serialNumber;
    private int zoneId;
    private String name;
    private String config;
    private String registryCode;
    private int way;
}
