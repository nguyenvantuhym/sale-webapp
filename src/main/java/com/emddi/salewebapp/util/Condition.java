package com.emddi.salewebapp.util;

import java.util.ArrayList;

public class Condition {
    private ArrayList<Condition> andCondition;

    private ArrayList<Condition> orCondition;

    public ArrayList<Condition> getAndCondition() {
        return andCondition;
    }

    public void setAndCondition(ArrayList<Condition> andCondition) {
        this.andCondition = andCondition;
    }

    public ArrayList<Condition> getOrCondition() {
        return orCondition;
    }

    public void setOrCondition(ArrayList<Condition> orCondition) {
        this.orCondition = orCondition;
    }
}
