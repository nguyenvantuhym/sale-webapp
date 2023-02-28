package com.emddi.salewebapp.util;

import com.emddi.salewebapp.db.Db;

import java.util.LinkedList;

/**
 * Executed commands
 */
public class Cmd {

    /**
     * Remote open door
     */
    public static String openDoor() {
        return "CONTROL DEVICE 01010103";
    }

    public static String closeDoor() {
        return "CONTROL DEVICE 01010100";
    }

    /**
     * return background verification's value
     */
    public static String returnBGVerifyData(String data) {
        //        String ret = "AUTH=FAILED\r\n" + data + "\r\n\r\n";//verify
        return "AUTH=SUCCESS\r\n" + data + "\r\nCONTROL DEVICE 1 1 1 15\r\n";
    }

    public static void addDeviceCommand(String sn, String cmd, String ticketUniqueId, String cardInfo) {
        System.out.println("\tadd cmd to queue\t" +sn+"\t"+ DateSQL.currentDateTimeMs());
        cmd = "C:" + Db.getDbId() + ":" + cmd;

        LinkedList<String> cmdList;
        if (Db.cmdListMap.get(sn) == null) {
            cmdList = new LinkedList<>();
        } else {
            cmdList = Db.cmdListMap.get(sn);
        }
        cmdList.add(cmd);
        Db.cmdListMap.put(sn, cmdList);
        Integer cmdId = Integer.parseInt(cmd.split(":")[1]);
        String[] cmdArr = new String[6];//存放命令及命令执行结果save command and its result
        cmdArr[0] = cmd;
        cmdArr[1] = "";
        cmdArr[2] = sn;
        cmdArr[3] = "";
        cmdArr[4] = ticketUniqueId;
        cmdArr[5] = cardInfo;
        Db.cmdMap.put(cmdId, cmdArr);
    }
    public static void addDeviceCommand(String sn, String cmd) {
        addDeviceCommand(sn, cmd, "", "");
    }

    public static void addTicketOpenDoorCmd(String sn, String ticketUniqueId) {
        addDeviceCommand(sn, openDoor(), ticketUniqueId, "");
    }


    public static void addCardOpenDoorCmd(String serialNumber, String cardInfo) {
        addDeviceCommand(serialNumber, openDoor(), "", cardInfo);
    }
}
