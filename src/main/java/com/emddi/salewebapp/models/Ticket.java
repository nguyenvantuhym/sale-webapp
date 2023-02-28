package com.emddi.salewebapp.models;

import com.emddi.salewebapp.constants.PriceStatus;
import com.emddi.salewebapp.constants.PriceType;
import com.emddi.salewebapp.constants.TicketStatus;
import com.emddi.salewebapp.constants.TicketType;
import com.emddi.salewebapp.util.DateSQL;
import com.emddi.salewebapp.util.QRCode;
import com.google.zxing.WriterException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;



@Getter
@Setter
@Builder(toBuilder = true)
public class Ticket {
    private Integer ticketId;
    private String uuid;
    private Integer employeeId;
    private Integer serviceId;

    private Integer priceId;
    private Integer employeeScan;

    private Integer userId;
    private Integer futureTicketId;
    private String serialNumber;
    private String bookingId;
    private Integer gateId;
    private String cardNo;
    private Integer status;
    private Integer type;
    private String note;
    private Integer price;
    private Integer usdPrice;
    private Integer paymentMethod;
    private String checkinTime;
    private String checkinLogs;
    private Integer printed;
    private String printedTime;
    private Integer timeUsing;
    private String usageTime;
    private Integer sendInvoice;
    private String customerName;
    private String customerPhone;
    private String email;
    private String companyName;
    private String companyAddress;
    private String taxCode;
    private Integer walkInGuest;
    private Integer sync;
    private String createdAt;

    private String cancelTime;
    private String employeeCancel;
    private Integer userCancel;
    private String cardNoUnique;

    private Gate gate;
    private String updatedAt;
    private Employee employee;
    private Price priceObj;
    private Service serviceObj;


    public String toQrBase64(int width, int height) throws IOException, WriterException {
        return QRCode.getQrCodeImage(this.cardNo, width, height);
    }

    public String formatDateTime(String patternSrc, String patternDest, String dateStr) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat(patternSrc);
        Date date = formatter1.parse(dateStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDest);
        return dateFormat.format(date);
    }

//    public void setPrintedTime(String printedTime) {
//        String[] printedTimes = printedTime.split("\\|");
//        this.printedTime = printedTimes[printedTimes.length - 1];
//    }

    public String getLastPrintedTime() {
        JSONArray printedArray = new JSONArray(printedTime);
        JSONObject lastPrintedTime = printedArray.getJSONObject(printedArray.length() - 1);
        return lastPrintedTime.getString("time");
    }

    public boolean checkAvailability() {
        if (this.ticketId == null || this.ticketId == 0) return false;

        if (this.getPriceObj().getStatus() != PriceStatus.ACTIVE) return false;

        int status = this.getStatus();
        int priceType = this.getPriceObj().getType();

        if (status == TicketStatus.USER_CANCELLED ||
                status == TicketStatus.EMP_CANCELLED ||
                status == TicketStatus.BOOK_ON_WEB_NOT_PAID) return false;

        if (status == TicketStatus.SCANNED && priceType == PriceType.MOT_LUOT) return false;

        int countSuccess = 0;
        if (this.getCheckinLogs() != null) {
            JSONArray checkinLogs = new JSONArray(this.getCheckinLogs());

            for (int i = 0; i < checkinLogs.length(); i++) {
                JSONObject checkinLog = checkinLogs.getJSONObject(i);
                if (checkinLog.getBoolean("success")) {
                    countSuccess++;
                }
            }
        }

        if (priceType == PriceType.MOT_LUOT && countSuccess >= 1 ||
                priceType == PriceType.KHU_HOI && countSuccess >= 2) {
            return false;
        }

        switch (this.getType()) {
            case TicketType.VE_LE_DUNG_TRONG_NGAY:
            case TicketType.VE_DOAN_DUNG_TRONG_NGAY:
            case TicketType.VE_MUA_ONLINE:
                String usageTime = this.getUsageTime().substring(0, 10);
                LocalDate today = LocalDate.now(DateSQL.zoneId);

                if (usageTime.equals(today.toString())) {
                    return true;
                }
                break;

            case TicketType.VE_IN_TRUOC_DUNG_THEO_GIO:
                LocalDateTime usageStartTime = LocalDateTime.parse(this.getUsageTime().replace(" ", "T"));
                LocalDateTime usageEndTime = usageStartTime.plusHours(this.getTimeUsing());
                LocalDateTime now = LocalDateTime.now(DateSQL.zoneId);
                if (now.isAfter(usageStartTime) && now.isBefore(usageEndTime)) {
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean checkAvailabilityWithWay(Integer wayToCheck) {
        if (!this.checkAvailability()) {
            return false;
        }

        if (this.getCheckinLogs() == null) return true;

        JSONArray checkinLogsArray = new JSONArray(this.getCheckinLogs());

        boolean hasGoThrowThisWay = false;
        for (int i = 0; i < checkinLogsArray.length(); i++) {
            JSONObject checkinLog = checkinLogsArray.getJSONObject(i);
            int way = checkinLog.isNull("way") ? 0 : checkinLog.getInt("way");
            if (checkinLog.getBoolean("success") && way == wayToCheck)
                hasGoThrowThisWay = true;
        }

        return !hasGoThrowThisWay;
    }

    public String getTypeText() {
        String type = "---";
        switch (this.getType()) {
            case TicketType.VE_LE_DUNG_TRONG_NGAY:
                type = "Vé lẻ bán trực tiếp";
                break;
            case TicketType.VE_DOAN_DUNG_TRONG_NGAY:
                type = "Vé đoàn";
                break;
            case TicketType.VE_IN_TRUOC_DUNG_THEO_GIO:
                type = "Vé in trước";
                break;
            case TicketType.VE_MUA_ONLINE:
                type = "Vé đặt online";
                break;
        }
        return type;
    }
}
