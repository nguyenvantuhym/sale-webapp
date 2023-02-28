package com.emddi.salewebapp.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@Builder(toBuilder = true)
public class Gate {
    private int id;
    private int officeId;
    private String name;
    private int status;
    private String description;
    private int sync;
    private String created_at;
    private String updated_at;

    public JSONObject toJson() {
        return new JSONObject(this);
    }
}
