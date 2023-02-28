package com.emddi.salewebapp.db;


import com.emddi.salewebapp.constants.PriceType;
import com.emddi.salewebapp.constants.SyncStatus;
import com.emddi.salewebapp.constants.TicketStatus;
import com.emddi.salewebapp.db.tablecol.PriceCol;
import com.emddi.salewebapp.db.tablecol.TicketCol;
import com.emddi.salewebapp.models.*;
import com.emddi.salewebapp.synchronize.jsonkey.PriceKey;
import com.emddi.salewebapp.synchronize.jsonkey.TicketKey;
import com.emddi.salewebapp.util.DateSQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketDao extends Database {
    String UPDATE_SYNC_STATUS = "UPDATE ticket SET sync = ? WHERE uuid = ?";

    public boolean saveBatch(List<Ticket> tickets) throws SQLException {
        Connection connection = createConnection();
        connection.setAutoCommit(false);

        String INSERT_SQL = "INSERT INTO ticket (uuid, service_id, price_id, gate_id, employee_id, card_no," +
                " usage_time, customer_name, customer_phone, type, future_ticket_id, printed_time, price, payment_method)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(INSERT_SQL);

        for (Ticket ticket : tickets) {
            statement.setString(1, ticket.getUuid());
            statement.setInt(2, ticket.getServiceId());
            statement.setInt(3, ticket.getPriceId());
            statement.setInt(4, ticket.getGateId());
            statement.setInt(5, ticket.getEmployeeId());
            statement.setString(6, ticket.getCardNo());
            statement.setString(7, ticket.getUsageTime());
            statement.setString(8, ticket.getCustomerName());
            statement.setString(9, ticket.getCustomerPhone());
            statement.setInt(10, ticket.getType());
            statement.setObject(11, ticket.getFutureTicketId());
            statement.setString(12, ticket.getPrintedTime());
            statement.setInt(13, ticket.getPriceObj().getPrice());
            statement.setInt(14, ticket.getPaymentMethod());

            statement.addBatch();
        }

        int[] inserted = statement.executeBatch();

        connection.commit();
        connection.close();

        return true;
    }

    public JSONObject updateRecordByRecode(ArrayList<Ticket> tickets) {
        Connection connection = createConnection();

        ArrayList<String> idsSuccess = new ArrayList<>();

        ArrayList<String> idsFailed = new ArrayList<>();
        for (Ticket ticket : tickets) {
            String UPDATE_SQL = "" +
                    "UPDATE ticket " +
                    "SET cancel_time = ?, printed_time = ?, type = ?, employee_cancel = ?, printed = ?, " +
                    "card_no = ?, updated_at = ?, employee_scan = ?, checkin_logs = ?, card_no_unique = ?, user_cancel = ?, " +
                    " status = ?, sync = 1, user_id=?, booking_id=? " +
                    "WHERE uuid = ?";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(UPDATE_SQL);

                statement.setString(1, ticket.getCancelTime());
                statement.setString(2, ticket.getPrintedTime());
                statement.setInt(3, ticket.getType());
                statement.setString(4, ticket.getEmployeeCancel());
                statement.setInt(5, ticket.getPrinted());
                statement.setString(6, ticket.getCardNo());
                statement.setString(7, ticket.getUpdatedAt());
                statement.setObject(8, ticket.getEmployeeScan());
                statement.setString(9, ticket.getCheckinLogs());
                statement.setString(10, ticket.getCardNoUnique());
                statement.setObject(11, ticket.getUserCancel());
                statement.setObject(12, ticket.getStatus());
                statement.setObject(13, ticket.getUserId());
                statement.setString(14, ticket.getBookingId());

                statement.setString(15, ticket.getUuid());

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(ticket.getUuid());
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ticket.getUuid());
                    idsFailed.add(ticket.getUuid());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", ticket.getUuid());
                idsFailed.add(ticket.getUuid());
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    public JSONObject saveRecordByRecord(ArrayList<Ticket> tickets) {
        Connection connection = createConnection();

        ArrayList<String> idsSuccess = new ArrayList<>();

        ArrayList<String> idsFailed = new ArrayList<>();
        for (Ticket ticket : tickets) {
            String INSERT_SQL = "INSERT INTO ticket (uuid, time_using, price_id, printed, payment_method, status, created_at, updated_at, " +
                    " service_id, gate_id, employee_id, card_no," +
                    " usage_time, customer_name, customer_phone, type, future_ticket_id, printed_time, price, sync," +
                    " employee_scan, user_id, booking_id, checkin_logs)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(INSERT_SQL);

                statement.setString(1, ticket.getUuid());
                statement.setObject(2, ticket.getTimeUsing());
                statement.setObject(3, ticket.getPriceId());
                statement.setObject(4, ticket.getPrinted());
                statement.setObject(5, ticket.getPaymentMethod());
                statement.setObject(6, ticket.getStatus());
                statement.setObject(7, ticket.getCreatedAt());
                statement.setObject(8, ticket.getUpdatedAt());
                statement.setObject(9, ticket.getServiceId());
                statement.setObject(10, ticket.getGateId());
                statement.setObject(11, ticket.getEmployeeId());
                statement.setString(12, ticket.getCardNo());
                statement.setString(13, ticket.getUsageTime());
                statement.setString(14, ticket.getCustomerName());
                statement.setString(15, ticket.getCustomerPhone());
                statement.setObject(16, ticket.getType());
                statement.setObject(17, ticket.getFutureTicketId());
                statement.setString(18, ticket.getPrintedTime());
                statement.setObject(19, ticket.getPrice());
                statement.setObject(20, SyncStatus.SYNC);
                statement.setObject(21, ticket.getEmployeeScan());
                statement.setObject(22, ticket.getUserId());
                statement.setString(23, ticket.getBookingId());
                statement.setString(24, ticket.getCheckinLogs());

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(ticket.getUuid());
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ticket.getUuid());
                    idsFailed.add(ticket.getUuid());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", ticket.getUuid());
                idsFailed.add(ticket.getUuid());
            }

        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    public boolean updateScanned(String uuid, int way) throws SQLException {
        Ticket ticket = getByUuid(uuid);
        if (ticket == null) return false;

        JSONArray checkinLogsArray = new JSONArray();
        if (ticket.getCheckinLogs() != null) {
            checkinLogsArray = new JSONArray(ticket.getCheckinLogs());
        }
        boolean succeedThisWay = false;

        for (int i = 0; i < checkinLogsArray.length(); i++) {
            JSONObject checkinLog = checkinLogsArray.getJSONObject(i);
            int way1 = checkinLog.isNull("way") ? 0 : checkinLog.getInt("way");
            if (checkinLog.getBoolean("success") && way1 == way)
                succeedThisWay = true;
        }

        if (succeedThisWay) {
            return false;
        }

        Connection connection = createConnection();
        String SCANNED_SQL = "UPDATE ticket " +
                " SET status = ?, sync = ?, checkin_logs = ? WHERE uuid = ?";
        PreparedStatement statement = connection.prepareStatement(SCANNED_SQL);

        int sync = ticket.getSync();
        if (ticket.getSync() == SyncStatus.SYNC) {
            sync = SyncStatus.UPDATE;
        } else if (ticket.getSync() == SyncStatus.UPDATE) {
            sync = SyncStatus.UPDATE;
        }

        int nextStatus = ticket.getStatus();
        if (ticket.getPriceObj().getType() == PriceType.MOT_LUOT) {
            nextStatus = TicketStatus.SCANNED;
        } else if (ticket.getPriceObj().getType() == PriceType.KHU_HOI) {
            if (ticket.getStatus() == TicketStatus.PAID_NOT_SCAN) {
                nextStatus = TicketStatus.QUET_MOT_LUOT;
            } else if (ticket.getStatus() == TicketStatus.QUET_MOT_LUOT) {
                nextStatus = TicketStatus.SCANNED;
            }
        }

        JSONObject checkinLogObject = new JSONObject();
//        printedObject.put("id", employeeId);
        checkinLogObject.put("time", DateSQL.currentDateTime());
        checkinLogObject.put("success", true);
        checkinLogObject.put("way", way);
        checkinLogsArray.put(checkinLogObject);

        statement.setInt(1, nextStatus);
        statement.setInt(2, sync);
        statement.setString(3, checkinLogsArray.toString());
        statement.setString(4, uuid);

        int success = statement.executeUpdate();

        connection.close();

        return success > 0;
    }

    public void updateScanTime(String uuid, int way, boolean result) throws SQLException {
        Ticket ticket = getByUuid(uuid);
        if (ticket == null) return;

        Connection connection = createConnection();
        String SCANNED_SQL = "UPDATE ticket SET checkin_logs = ?, sync = ? WHERE uuid = ?";
        PreparedStatement statement = connection.prepareStatement(SCANNED_SQL);


        JSONArray checkinLogsArray = new JSONArray();
        if (ticket.getCheckinLogs() != null) {
            checkinLogsArray = new JSONArray(ticket.getCheckinLogs());
        }
        if (result) {
            boolean succeedThisWay = false;

            for (int i = 0; i < checkinLogsArray.length(); i++) {
                JSONObject checkinLog = checkinLogsArray.getJSONObject(i);
                int way1 = checkinLog.isNull("way") ? 0 : checkinLog.getInt("way");
                if (checkinLog.getBoolean("success") && way1 == way)
                    succeedThisWay = true;
            }

            if (succeedThisWay) {
                return;
            }
        }

        JSONObject checkinLogObject = new JSONObject();
//        printedObject.put("id", employeeId);
        checkinLogObject.put("time", DateSQL.currentDateTime());
        checkinLogObject.put("success", result);
        checkinLogObject.put("way", way);
        checkinLogsArray.put(checkinLogObject);

        int sync = SyncStatus.NEW;
        if (ticket.getSync() == SyncStatus.SYNC) {
            sync = SyncStatus.UPDATE;
        } else if (ticket.getSync() == SyncStatus.UPDATE) {
            sync = SyncStatus.UPDATE;
        }

        statement.setString(1, checkinLogsArray.toString());
        statement.setInt(2, sync);
        statement.setString(3, uuid);

        statement.executeUpdate();

        connection.close();
    }

    public JSONArray findTicketToSync(int syncStatus) {
        System.out.println("Ticket::findTicketToSync: Start " + DateSQL.currentDateTime());
        Connection connection = createConnection();
        String FIND_NEW_CREATE_SQL = "SELECT * FROM ticket WHERE sync = ? LIMIT 50";

        JSONArray tickets = new JSONArray();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_NEW_CREATE_SQL);
            statement.setInt(1, syncStatus);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                JSONObject ticket = new JSONObject();
                ticket.put(TicketCol.uuid, rs.getString(TicketCol.uuid));
                ticket.put(TicketCol.status, rs.getInt(TicketCol.status));
                ticket.put(TicketCol.printed, rs.getInt(TicketCol.printed));
                ticket.put(TicketCol.printedTime, rs.getString(TicketCol.printedTime));
                ticket.put(TicketCol.updatedAt, rs.getString(TicketCol.updatedAt));
                if (syncStatus == SyncStatus.NEW) {
                    ticket.put(TicketCol.serviceId, rs.getInt(TicketCol.serviceId));
                    ticket.put(TicketCol.priceId, rs.getInt(TicketCol.priceId));
                    ticket.put(TicketCol.employeeId, rs.getInt(TicketCol.employeeId));
                    ticket.put(TicketCol.gateId, rs.getInt(TicketCol.gateId));
                    ticket.put(TicketCol.cardNo, rs.getString(TicketCol.cardNo));
                    ticket.put(TicketCol.type, rs.getInt(TicketCol.type));
                    ticket.put(TicketCol.price, rs.getInt(TicketCol.price));
                    ticket.put(TicketCol.paymentMethod, rs.getInt(TicketCol.paymentMethod));
                    ticket.put(TicketCol.timeUsing, rs.getInt(TicketCol.timeUsing));
                    ticket.put(TicketCol.usageTime, rs.getString(TicketCol.usageTime));
                    ticket.put(TicketCol.createdAt, rs.getString(TicketCol.createdAt));

                    ticket.put(TicketCol.cardNoUnique, rs.getString(TicketCol.cardNoUnique));
                    ticket.put(TicketCol.futureTicketId, rs.getString(TicketCol.futureTicketId));
                    ticket.put(TicketCol.employeeScan, rs.getInt(TicketCol.employeeScan));
                    ticket.put(TicketCol.userId, rs.getInt(TicketCol.userId));
                    ticket.put(TicketCol.bookingId, rs.getString(TicketCol.bookingId));
                    ticket.put(TicketCol.checkinLogs, rs.getString(TicketCol.checkinLogs));
                }
                if (syncStatus == SyncStatus.UPDATE) {
                    ticket.put(TicketCol.employeeScan, rs.getInt(TicketCol.employeeScan));
                    ticket.put(TicketCol.checkinLogs, rs.getString(TicketCol.checkinLogs));

                    ticket.put(TicketCol.employeeCancel, rs.getString(TicketCol.employeeCancel));
                    ticket.put(TicketCol.userCancel, rs.getString(TicketCol.userCancel));
                    ticket.put(TicketCol.cancelTime, rs.getString(TicketCol.cancelTime));
                }

                tickets.put(ticket);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Ticket::findTicketToSync: Done " + DateSQL.currentDateTime());

        return tickets;
    }

    public boolean updateSyncStatus(JSONArray uuids, int status) {
        if (uuids.length() == 0) return true;

        int[] inserted = new int[0];
        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(UPDATE_SYNC_STATUS);

            for (Object uuid : uuids) {
                statement.setInt(1, status);
                statement.setString(2, uuid.toString());

                statement.addBatch();
            }

            inserted = statement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted.length > 0;
    }

    public Ticket getByCardNumberBeforeNow(String cardNumber) throws SQLException {
        System.out.println("start connect " + DateSQL.currentDateTimeMs());
        Connection connection = createConnection();
        String SQL = "SELECT " +
                "p.price_name, p.type AS price_type, p.status AS price_status, g.name AS gate_name, e.name AS employee_name, s.type AS service_type, " +
                "t.sync AS sync_ticket, t.*" +
                "FROM ticket t JOIN price p ON p.price_id = t.price_id " +
                "LEFT JOIN gate g ON g.id = t.gate_id " +
                "LEFT JOIN employee e ON e.employee_id = t.employee_id " +
                "JOIN service s ON s.service_id = t.service_id " +
                "WHERE card_no = ? AND usage_time < ? ORDER BY t.ticket_id DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setString(1, cardNumber);
        statement.setString(2, DateSQL.currentDateTime());

        Ticket ticket = getTicket(statement);
        connection.close();

        return ticket;
    }

    public Ticket getByCardNumber(String cardNumber) throws SQLException {
        Connection connection = createConnection();
        String SQL = "SELECT " +
                "p.price_name, p.type AS price_type, p.status AS price_status, g.name AS gate_name, e.name AS employee_name, s.type AS service_type, " +
                "t.sync AS sync_ticket, t.*" +
                "FROM ticket t JOIN price p ON p.price_id = t.price_id " +
                "LEFT JOIN gate g ON g.id = t.gate_id " +
                "LEFT JOIN employee e ON e.employee_id = t.employee_id " +
                "JOIN service s ON s.service_id = t.service_id " +
                "WHERE card_no = ? ORDER BY t.ticket_id DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setString(1, cardNumber);

        Ticket ticket = getTicket(statement);
        connection.close();

        return ticket;
    }

    public Ticket getByUuid(String uuid) throws SQLException {
        Connection connection = createConnection();
        String SQL = "SELECT " +
                "p.price_name, p.type AS price_type, p.status AS price_status, g.name AS gate_name, e.name AS employee_name, s.type AS service_type," +
                " t.sync AS sync_ticket, t.*" +
                "FROM ticket t " +
                "JOIN price p ON p.price_id = t.price_id " +
                "LEFT JOIN gate g ON g.id = t.gate_id " +
                "LEFT JOIN employee e ON e.employee_id = t.employee_id " +
                "JOIN service s ON s.service_id = t.service_id " +
                "WHERE uuid = ? ORDER BY id DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setString(1, uuid);

        Ticket ticket = getTicket(statement);
        connection.close();

        return ticket;
    }

    private Ticket getTicket(PreparedStatement statement) throws SQLException {
        System.out.println(" get ticket -------");
        System.out.println(" exec  " + DateSQL.currentDateTimeMs());
        ResultSet resultSet = statement.executeQuery();
        Ticket ticket = null;

        if (resultSet.next()) {
            ticket = Ticket.builder()
                    .ticketId(resultSet.getInt(TicketCol.ticketId))
                    .uuid(resultSet.getString(TicketCol.uuid))
                    .cardNo(resultSet.getString(TicketCol.cardNo))
                    .priceId(resultSet.getInt(TicketCol.priceId))
                    .gateId(resultSet.getInt(TicketCol.gateId))
                    .employeeId(resultSet.getObject(TicketCol.employeeId, Integer.class))
                    .usageTime(resultSet.getString(TicketCol.usageTime))
                    .type(resultSet.getInt(TicketCol.type))
                    .status(resultSet.getInt(TicketCol.status))
                    .checkinTime(resultSet.getString(TicketCol.checkinTime))
                    .checkinLogs(resultSet.getString(TicketCol.checkinLogs))
                    .printedTime(resultSet.getString(TicketCol.printedTime))
                    .timeUsing(resultSet.getInt(TicketCol.timeUsing))
                    .createdAt(resultSet.getString(TicketCol.createdAt))
                    .sync(resultSet.getInt("sync_ticket"))
                    .build();

            Price price = Price.builder()
                    .priceId(resultSet.getInt(TicketCol.priceId))
                    .priceName(resultSet.getString(PriceCol.priceName))
                    .type(resultSet.getInt("price_type"))
                    .status(resultSet.getInt("price_status"))
                    .build();
            ticket.setPriceObj(price);

            Service service = Service.builder()
                    .serviceId(resultSet.getInt(TicketCol.serviceId))
                    .type(resultSet.getString("service_type"))
                    .build();
            ticket.setServiceObj(service);

            Gate gate = Gate.builder().build();
            if ((Integer) resultSet.getInt(TicketCol.gateId) != 0) {
                gate.setId(resultSet.getInt(TicketCol.gateId));
                gate.setName(resultSet.getString("gate_name"));

            }
            ticket.setGate(gate);

            Employee employee = Employee.builder().build();
            if ((Integer) resultSet.getInt(TicketCol.employeeId) != 0) {

                employee.setEmployeeId(resultSet.getInt(TicketCol.employeeId));
                employee.setName(resultSet.getString("employee_name"));
            }
            ticket.setEmployee(employee);
        }

        System.out.println(" end  " + DateSQL.currentDateTimeMs());
        return ticket;
    }


    public JSONObject findTicketByFilter(FilterTicket filterTicket) throws SQLException {
        Connection connection = createConnection();
        String FIND_TICKET_BY_FILTER_SQL = "SELECT t.status, t.ticket_id AS ticket_id, t.created_at AS created_at, t.card_no AS card_no," +
                " p.price_name AS price_name, t.price AS price, t.payment_method " +
                " FROM ticket AS t INNER JOIN price AS p ON t.price_id = p.price_id " +
                "WHERE t.created_at BETWEEN ? AND ? " +
                "AND (UPPER(?) = 'ALL' OR t.price_id = ?) AND (? = '' OR t.card_no = ?) AND (UPPER(?) = 'ALL' OR (? = -2 AND t.status = -3) OR t.status = ?)" +
                "AND (t.employee_id = ? OR t.employee_id IS NULL) AND (UPPER(?) = 'ALL' OR t.type = ?) " +
                "AND (? = 'ALL' OR t.payment_method = ? ) " +
                " ORDER BY t.created_at DESC" +
                " LIMIT ? OFFSET ?";

        JSONArray tickets = new JSONArray();
        PreparedStatement statement = connection.prepareStatement(FIND_TICKET_BY_FILTER_SQL);
        statement.setString(1, filterTicket.getDateFrom());
        statement.setString(2, filterTicket.getDateTo());

        statement.setString(3, filterTicket.getService());
        statement.setString(4, filterTicket.getService());

        statement.setString(5, filterTicket.getTicketCode());
        statement.setString(6, filterTicket.getTicketCode());

        statement.setString(7, filterTicket.getTicketStatus());
        statement.setString(8, filterTicket.getTicketStatus());
        statement.setString(9, filterTicket.getTicketStatus());

        statement.setString(10, filterTicket.getStaff());

        statement.setString(11, filterTicket.getTicketType());
        statement.setString(12, filterTicket.getTicketType());

        statement.setString(13, filterTicket.getPaymentMethod());
        statement.setString(14, filterTicket.getPaymentMethod());


        statement.setInt(15, filterTicket.getPageSize() + 1);
        statement.setInt(16, (filterTicket.getPageNumber() - 1) * filterTicket.getPageSize());

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            JSONObject ticket = new JSONObject();
            ticket.put(TicketKey.ticketId, resultSet.getInt(TicketCol.ticketId));
            ticket.put(TicketKey.cardNo, resultSet.getString(TicketCol.cardNo));
            ticket.put(PriceKey.status, resultSet.getInt(TicketCol.status));
            ticket.put(PriceKey.price, resultSet.getInt(TicketCol.price));
            ticket.put(TicketCol.paymentMethod, resultSet.getString(TicketCol.paymentMethod));
            ticket.put(PriceKey.priceName, resultSet.getString(PriceCol.priceName));
            ticket.put(TicketKey.createdAt, resultSet.getString(TicketCol.createdAt));
            tickets.put(ticket);
        }
        connection.close();
        Boolean hasNextPage = false;
        if (tickets.length() > filterTicket.getPageSize()) {
            tickets.remove(tickets.length() - 1);
            hasNextPage = true;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ticket_list", tickets);
        jsonObject.put("has_next_page", hasNextPage);
        return jsonObject;
    }


    public JSONObject saleReport(FilterTicket filterTicket) throws SQLException {
        Connection connection = createConnection();
        String FIND_TICKET_BY_FILTER_SQL =
                "SELECT p.price_id AS price_id, p.price_name AS price_name, COUNT(t.price_id) AS ticket_count,  SUM(t.price) AS price_sum " +
                        "                FROM ticket AS t INNER JOIN price AS p ON t.price_id = p.price_id " +
                        "                WHERE  t.created_at BETWEEN ? AND ? " +
                        "                AND (UPPER(?) = 'ALL' OR t.price_id = ?) " +
                        "                AND (? = '' OR t.card_no = ?) " +
                        "                AND (UPPER(?) = 'ALL' OR t.status = ?) " +
                        "                AND (t.employee_id = ? OR t.employee_id IS NULL) " +
                        "                AND (UPPER(?) = 'ALL' OR t.type = ?) " +
                        "                AND t.status <> -3  AND t.status <> -2 " +
                        "                AND (? = 'ALL' OR t.payment_method = ? ) " +
                        "                GROUP BY t.price_id , p.price_name";

        JSONArray reportList = new JSONArray();
        PreparedStatement statement = connection.prepareStatement(FIND_TICKET_BY_FILTER_SQL);
        statement.setString(1, filterTicket.getDateFrom());
        statement.setString(2, filterTicket.getDateTo());

        statement.setString(3, filterTicket.getService());
        statement.setString(4, filterTicket.getService());

        statement.setString(5, filterTicket.getTicketCode());
        statement.setString(6, filterTicket.getTicketCode());

        statement.setString(7, filterTicket.getTicketStatus());
        statement.setString(8, filterTicket.getTicketStatus());

        statement.setString(9, filterTicket.getStaff());

        statement.setString(10, filterTicket.getTicketType());
        statement.setString(11, filterTicket.getTicketType());


        statement.setString(12, filterTicket.getPaymentMethod());
        statement.setString(13, filterTicket.getPaymentMethod());


        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            JSONObject report = new JSONObject();
            report.put("price_id", resultSet.getInt("price_id"));
            report.put("price_name", resultSet.getString("price_name"));

            report.put("ticket_count", resultSet.getString("ticket_count"));
            report.put("price_sum", resultSet.getInt("price_sum"));
            reportList.put(report);
        }
        connection.close();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("report", reportList);
        return jsonObject;
    }

    public boolean updatePrintTimeByIds(List<Integer> ids, int employeeId) throws SQLException {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Connection connection = createConnection();

        String SQL = String.format("UPDATE ticket SET sync = (CASE " +
                " WHEN sync = 1 OR sync = 3 THEN 2 " +
                " ELSE sync " +
                " END )" +
                ", printed_time = (" +
                "    CASE " +
                "         WHEN printed_time is NULL THEN CONCAT('[',?,']')\n" +
                "         ELSE  CONCAT(SUBSTRING(printed_time, 1, (LENGTH(printed_time)-1)),',',?, ']')\n" +
                "    END)\n" +
                "              WHERE ticket_id IN (%s)", inSql);

        String newScanTime = DateSQL.currentDateTime();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", newScanTime);
        jsonObject.put("emp_id", employeeId);

        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setString(1, jsonObject.toString());
        statement.setString(2, jsonObject.toString());
        for (int i = 0; i < ids.size(); i++) {
            statement.setInt(i + 3, ids.get(i));
        }
        int success = statement.executeUpdate();

        connection.close();

        return success > 0;
    }

    public List<Ticket> findByIds(ArrayList<Integer> ids) throws SQLException {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Connection connection = createConnection();

        String SQL = String.format("SELECT t.*, p.*, e.*, g.name AS gate_name FROM ticket AS t LEFT JOIN price AS p ON t.price_id = p.price_id " +
                "LEFT JOIN employee AS e ON t.employee_id = e.employee_id  " +
                "LEFT JOIN gate AS g ON t.gate_id = g.id  " +
                "  WHERE ticket_id IN (%s)", inSql);

        PreparedStatement statement = connection.prepareStatement(SQL);
        for (int i = 0; i < ids.size(); i++) {
            statement.setInt(i + 1, ids.get(i));
        }
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Ticket> tickets = new ArrayList<>();

        while (resultSet.next()) {
            Ticket ticket = Ticket.builder().build();
            ticket.setTicketId(resultSet.getInt(TicketCol.ticketId));
            ticket.setPriceId(resultSet.getInt(PriceCol.priceId));
            ticket.setGateId(resultSet.getInt(TicketCol.gateId));
            ticket.setEmployeeId(resultSet.getInt(TicketCol.employeeId));
            ticket.setUsageTime(resultSet.getString(TicketCol.usageTime));
            ticket.setType(resultSet.getInt(TicketCol.type));
            ticket.setStatus(resultSet.getInt(TicketCol.status));
            ticket.setCheckinTime(resultSet.getString(TicketCol.checkinTime));
            ticket.setCreatedAt(resultSet.getString(TicketCol.createdAt));
            ticket.setPrinted(resultSet.getInt(TicketCol.printed));
            ticket.setPrintedTime(resultSet.getString(TicketCol.printedTime));

            ticket.setUuid(resultSet.getString(TicketCol.uuid));
            ticket.setCardNo(resultSet.getString(TicketCol.cardNo));
            ticket.setCustomerName(resultSet.getString(TicketCol.customerName));
            ticket.setCustomerPhone(resultSet.getString(TicketCol.customerPhone));

            Price ticketType = Price.builder()
                    .priceId(resultSet.getInt(PriceCol.priceId))
                    .price(resultSet.getInt(PriceCol.price))
                    .priceName(resultSet.getString(PriceCol.priceName))
                    .usdPrice(resultSet.getInt(PriceCol.usdPrice))
                    .status(resultSet.getInt(PriceCol.status))
                    .build();
            Employee employee = Employee.builder()
                    .name(resultSet.getString("name"))
                    .build();

            Gate gate = Gate.builder().name(resultSet.getString("gate_name")).build();
            ticket.setEmployee(employee);
            ticket.setGate(gate);

            ticket.setPriceObj(ticketType);

            tickets.add(ticket);
        }
        connection.close();

        return tickets;
    }

    public boolean cancelTicketByIds(ArrayList<Integer> ids, int employeeId) throws SQLException {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Connection connection = createConnection();
        String SQL = String.format("UPDATE ticket SET cancel_time = ? ,status = -3, employee_cancel = ?, " +
                " sync = (CASE " +
                "    WHEN sync = 1 OR sync = 3 THEN 2 " +
                "    ELSE sync " +
                "END )"
                + "  WHERE ticket_id IN (%s)", inSql);
        String currentDateTime = DateSQL.currentDateTime();
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setString(1, currentDateTime);
        statement.setInt(2, employeeId);

        for (int i = 0; i < ids.size(); i++) {
            statement.setInt(i + 3, ids.get(i));
        }
        int success = statement.executeUpdate();

        connection.close();

        return success > 0;
    }

    public ArrayList<Ticket> getTicketDemo() {
        ArrayList tickets = new ArrayList<>();

        Ticket ticket = Ticket.builder().build();
        ticket.setPrintedTime("[{\"time\":\"2023-01-01 00:00:00\",\"emp_id\":45}]");

        ticket.setCardNo("888888888");
        ticket.setCustomerName("customer Name");
        ticket.setCustomerPhone("0987654321");
        ticket.setUsageTime("2023-01-01 00:00:00");
        Price ticketType = Price.builder()
                .priceId(1)
                .status(1)
                .price(200000)
                .priceName("Vé Khu vui chơi VNPass - DEMO")
                .usdPrice(100)
                .build();
        Employee employee = Employee.builder()
                .name("Thu ngân demo")
                .build();

        Gate gate = Gate.builder().name("Quầy demo").build();
        ticket.setEmployee(employee);
        ticket.setGate(gate);

        ticket.setPriceObj(ticketType);
        tickets.add(ticket);
        return tickets;
    }

}
