package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Employee {
    private int employeeId;

    private int officeId;
    private String name;

    private String birthday;
    private String idCard;
    private String username;
    private String password;

    private int status;

    private String employeeCode;
    private String lastLogin;
    private int positionId;
    private String originally;
    private String address;
    private String imagePath;
    private int statusUpdate;
    private int sync;
    private String createdAt;
    private String updatedAt;

}
