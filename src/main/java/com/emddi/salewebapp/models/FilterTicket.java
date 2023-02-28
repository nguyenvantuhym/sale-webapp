package com.emddi.salewebapp.models;

public class FilterTicket {
    private String dateFrom;
    private String service;
    private String ticketCode;

    private String paymentMethod;

    public FilterTicket(String dateFrom, String service, String ticketCode, String ticketType, String dateTo, String staff, String ticketStatus, String paymentMethod, int pageSize, int pageNumber) {
        this(dateFrom, service, ticketCode, ticketType, dateTo, staff, ticketStatus, pageSize, pageNumber);
        this.paymentMethod = paymentMethod;

    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public FilterTicket(String dateFrom, String service, String ticketCode, String ticketType, int pageSize, int pageNumber, String dateTo, String staff, String ticketStatus) {
        this.dateFrom = dateFrom;
        this.service = service;
        this.ticketCode = ticketCode;
        this.ticketType = ticketType;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.dateTo = dateTo;
        this.staff = staff;
        this.ticketStatus = ticketStatus;
    }

    private String ticketType;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    private int pageSize;

    private int pageNumber;

    public String getDateFrom() {
        return dateFrom;
    }

    public FilterTicket(String dateFrom, String service, String ticketCode, String ticketType, String dateTo, String staff, String ticketStatus, int pageSize, int pageNumber) {
        this.dateFrom = dateFrom;
        this.service = service;
        this.ticketCode = ticketCode;
        this.ticketType = ticketType;
        this.dateTo = dateTo;
        this.staff = staff;
        this.ticketStatus = ticketStatus;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    private String dateTo;
    private String staff;
    private String ticketStatus;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
