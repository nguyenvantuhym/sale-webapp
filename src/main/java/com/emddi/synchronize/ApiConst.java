package com.emddi.synchronize;

import okhttp3.MediaType;

public class ApiConst {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String CHECKSUM_HEADER = "checksum";
    public static final String ROUTER_ID_PARAM = "router_id";

    public static final String PARAM_INSERT = "insert";
    public static final String PARAM_UPDATE = "update";
    public static final String PARAM_TARGET = "target";
    public static final String PARAM_CHECKSUM = "checksum";
    public static final String TARGET_TICKET = "ticket";

    public static final String TARGET_FUTURE_TICKET = "future_ticket";
    public static final String TARGET_FUTURE_TICKET_VALUE = "future_ticket_value";
    public static final String TARGET_EMPLOYEE = "employee";

    public static final String TARGET_EMPLOYEE_SERVICE = "employee_service";
    public static final String PATH_SYNC_INSERT_DOWN = "sync-insert";


    public static final String TARGET_CARD = "card";
    public static final String TARGET_CARD_HISTORY = "card_history";
    public static final String PARAM_TIME = "time";
    public static final String PARAM_SUCCESS = "success";
    public static final String PARAM_ERROR = "error";
    public static final String TARGET_SERVICE = "service";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_GATE = "gate";
    public static final String PARAM_ZONE = "zone";
    public static final String PARAM_CONTROL_DEVICE = "control_device";
    public static final String TARGET_CONTROL_DEVICE = "control_device";
    public static final String TARGET_GATE = "gate";
    public static final String TARGET_PRICE = "price";
}
