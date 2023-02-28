package com.emddi.salewebapp.db;

import com.emddi.salewebapp.db.tablecol.ServiceCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDao extends Database implements IDAO {
    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW = "INSERT INTO service (service_id, office_id, city_id, name, description, status, type, company_name, " +
                " address, tax_code, telephone, email, ticket_name, invoice_type, template_code, invoice, office_tax_code, " +
                " unit_name, iorder, created_at, updated_at, sync) " +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject service = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW);
            statement.setObject(1, service.isNull(ServiceCol.serviceId)? null: service.get(ServiceCol.serviceId));
            statement.setObject(2, service.isNull(ServiceCol.officeId)? null: service.get(ServiceCol.officeId));
            statement.setObject(3, service.isNull(ServiceCol.cityId)? null: service.get(ServiceCol.cityId));
            statement.setString(4, service.isNull(ServiceCol.name)? null: service.getString(ServiceCol.name));
            statement.setString(5, service.isNull(ServiceCol.description)? null: service.getString(ServiceCol.description));
            statement.setObject(6, service.isNull(ServiceCol.status)? null: service.get(ServiceCol.status));
            statement.setString(7, service.isNull(ServiceCol.type)? null: service.getString(ServiceCol.type));
            statement.setString(8, service.isNull(ServiceCol.companyName)? null: service.getString(ServiceCol.companyName));
            statement.setString(9, service.isNull(ServiceCol.address)? null: service.getString(ServiceCol.address));
            statement.setString(10, service.isNull(ServiceCol.taxCode)? null: service.getString(ServiceCol.taxCode));
            statement.setString(11, service.isNull(ServiceCol.telephone)? null: service.getString(ServiceCol.telephone));
            statement.setString(12, service.isNull(ServiceCol.email)? null: service.getString(ServiceCol.email));
            statement.setString(13, service.isNull(ServiceCol.ticketName)? null: service.getString(ServiceCol.ticketName));
            statement.setString(14, service.isNull(ServiceCol.invoiceType)? null: service.getString(ServiceCol.invoiceType));
            statement.setString(15, service.isNull(ServiceCol.templateCode)? null: service.getString(ServiceCol.templateCode));
            statement.setString(16, service.isNull(ServiceCol.invoice)? null: service.getString(ServiceCol.invoice));
            statement.setString(17, service.isNull(ServiceCol.officeTaxCode)? null: service.getString(ServiceCol.officeTaxCode));

            statement.setString(18, service.isNull(ServiceCol.unitName)? null: service.getString(ServiceCol.unitName));
            statement.setObject(19, service.isNull(ServiceCol.iorder)? null: service.get(ServiceCol.iorder));
            // sync
            statement.setString(20, service.isNull(ServiceCol.createdAt)? null: service.getString(ServiceCol.createdAt));
            statement.setString(21, service.isNull(ServiceCol.updatedAt)? null: service.getString(ServiceCol.updatedAt));
            statement.setObject(22, 1);

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(service.getInt(ServiceCol.serviceId));
                }
            } catch (SQLException e){
                idsFailed.add(service.getInt(ServiceCol.serviceId));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    @Override
    public JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW = "UPDATE service SET office_id=? , city_id=? , name=? , description=? , status=? , type=? ," +
                " company_name=? ,address=? , tax_code=? , telephone=? , email=? , ticket_name=? , invoice_type=? ," +
                " template_code=? , invoice=? , office_tax_code=? , unit_name=? , iorder=? , created_at=? , updated_at=? ," +
                " sync=? " +
                " WHERE service_id=? ";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject service = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW);
            try {
                statement.setObject(1, service.isNull(ServiceCol.officeId)? null: service.get(ServiceCol.officeId));
                statement.setObject(2, service.isNull(ServiceCol.cityId)? null: service.get(ServiceCol.cityId));
                statement.setString(3, service.isNull(ServiceCol.name)? null: service.getString(ServiceCol.name));
                statement.setString(4, service.isNull(ServiceCol.description)? null: service.getString(ServiceCol.description));
                statement.setObject(5, service.isNull(ServiceCol.status)? null: service.get(ServiceCol.status));
                statement.setString(6, service.isNull(ServiceCol.type)? null: service.getString(ServiceCol.type));
                statement.setString(7, service.isNull(ServiceCol.companyName)? null: service.getString(ServiceCol.companyName));
                statement.setString(8, service.isNull(ServiceCol.address)? null: service.getString(ServiceCol.address));
                statement.setString(9, service.isNull(ServiceCol.taxCode)? null: service.getString(ServiceCol.taxCode));
                statement.setString(10, service.isNull(ServiceCol.telephone)? null: service.getString(ServiceCol.telephone));
                statement.setString(11, service.isNull(ServiceCol.email)? null: service.getString(ServiceCol.email));
                statement.setString(12, service.isNull(ServiceCol.ticketName)? null: service.getString(ServiceCol.ticketName));
                statement.setString(13, service.isNull(ServiceCol.invoiceType)? null: service.getString(ServiceCol.invoiceType));
                statement.setString(14, service.isNull(ServiceCol.templateCode)? null: service.getString(ServiceCol.templateCode));
                statement.setString(15, service.isNull(ServiceCol.invoice)? null: service.getString(ServiceCol.invoice));
                statement.setString(16, service.isNull(ServiceCol.officeTaxCode)? null: service.getString(ServiceCol.officeTaxCode));

                statement.setString(17, service.isNull(ServiceCol.unitName)? null: service.getString(ServiceCol.unitName));
                statement.setObject(18, service.isNull(ServiceCol.iorder)? null: service.get(ServiceCol.iorder));

                statement.setString(19, service.isNull(ServiceCol.createdAt)? null: service.getString(ServiceCol.createdAt));
                statement.setString(20, service.isNull(ServiceCol.updatedAt)? null: service.getString(ServiceCol.updatedAt));
                statement.setObject(21, 1);
                statement.setObject(22, service.isNull(ServiceCol.serviceId)? null: service.get(ServiceCol.serviceId));

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(service.getInt(ServiceCol.serviceId));
                }
            } catch (SQLException e){
                idsFailed.add(service.getInt(ServiceCol.serviceId));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
