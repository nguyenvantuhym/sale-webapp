package com.emddi.salewebapp.synchronize;

import com.emddi.salewebapp.constants.ResponseStatus;
import com.emddi.salewebapp.constants.StaticConfig;
import com.emddi.salewebapp.models.*;
import com.emddi.salewebapp.synchronize.jsonkey.ResponseKey;
import com.emddi.salewebapp.util.Md5;
import com.emddi.salewebapp.util.RandomString;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Logger;

public class CloudRequest extends ApiCaller {
    public static final Logger LOGGER = Logger.getLogger(CloudRequest.class.getName());

    public static TicketOnCloud scanTicketOnCloud(String cardNumber, int wayThrough) {
        HttpUrl url = new CloudEndpoint().getScanTicketUrlBuilder().build();

        String time = "1" + new RandomString(9, new SecureRandom(), RandomString.digits).nextString();

        JSONObject formBodyData = new JSONObject();
        formBodyData.put("time", time);
        formBodyData.put("card_no", cardNumber);
        formBodyData.put("way", wayThrough);


        String checkSum = StaticConfig.checksumHashKey + "." + cardNumber + "." + time + "." + wayThrough;
        formBodyData.put(ApiConst.PARAM_CHECKSUM, Md5.hash(checkSum));

        RequestBody formBody = RequestBody.create(ApiConst.JSON, formBodyData.toString());

        Request request = new Request.Builder().url(url).post(formBody).build();

        TicketOnCloud ticketOnCloud = TicketOnCloud.builder().available(false).build();
        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                return ticketOnCloud;
            }

            JSONObject body = new JSONObject(response.body().string());

            if (body.getString(ResponseKey.code).equals(ResponseStatus.SUCCESSFUL)) {
                ticketOnCloud.setAvailable(true);

                JSONObject data = body.getJSONObject(ResponseKey.data);

                Ticket ticket = Ticket.builder()
                        .build();

                ticketOnCloud.setTicket(ticket);

                return ticketOnCloud;
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
        return ticketOnCloud;
    }

    public static TicketOnCloud checkTicketOnCloud(String cardNumber) {
        HttpUrl url = new CloudEndpoint().getCheckTicketUrlBuilder().build();

        String time = "1" + new RandomString(9, new SecureRandom(), RandomString.digits).nextString();

        JSONObject formBodyData = new JSONObject();
        formBodyData.put("time", time);
        formBodyData.put("card_no", cardNumber);


        String checkSum = StaticConfig.checksumHashKey + "." + cardNumber + "." + time;
        formBodyData.put(ApiConst.PARAM_CHECKSUM, Md5.hash(checkSum));

        RequestBody formBody = RequestBody.create(ApiConst.JSON, formBodyData.toString());

        Request request = new Request.Builder().url(url).post(formBody).build();

        TicketOnCloud ticketOnCloud = TicketOnCloud.builder().available(false).build();
        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                return ticketOnCloud;
            }

            JSONObject body = new JSONObject(response.body().string());

            if (body.getString(ResponseKey.code).equals(ResponseStatus.SUCCESSFUL)) {

                JSONObject data = body.getJSONObject(ResponseKey.data);
                ticketOnCloud.setAvailable(data.getBoolean("available"));

                Ticket ticket = Ticket.builder()
                        .cardNo(data.getString("cardNo"))
                        .type(data.getInt("type"))
                        .usageTime(data.getString("usageTime"))
                        .timeUsing(data.getInt("time_using"))
                        .checkinLogs(data.getString("checkinLogs"))
                        .printedTime(data.getString("printTime"))
                        .createdAt(data.getString("createdAt"))
                        .employee(Employee.builder().name(data.getString("staffName")).build())
                        .priceObj(Price.builder().priceName(data.getString("priceName")).build())
                        .gate(Gate.builder().name(data.getString("gateName")).build())
                        .build();

                ticketOnCloud.setTicket(ticket);

                return ticketOnCloud;
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
        return ticketOnCloud;
    }
}

