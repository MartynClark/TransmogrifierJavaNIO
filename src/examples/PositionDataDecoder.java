package examples;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * User: martyn
 * Date: 08/03/2013
 * Time: 10:05
 */
public class PositionDataDecoder {

    public static PositionData decodePacket(byte[] packet) {

        System.out.println("NEW PACKET RECEIVED: " + new String(packet).trim()); // log packet

        int seqId;
        char val = 0;
        int crcValue = 0;
        long imeINumber = 0;
        List<Record> records = null;
        if (packet.length > 0) {
            String[] tokens = bytesToUtf(packet).split("\\u000A"); // <LF> split string
            records = new ArrayList<>();
            for (String token : tokens) {
                String[] tmp = token.split("\\u002C"); // first token contains header details delimiter (",")
                if (tmp[0].startsWith("\u0040")) { // with header?
                    crcValue = Integer.parseInt(tmp[1], 16);
                    seqId = Integer.parseInt(tmp[3]);
                    val = (char) seqId;
                    imeINumber = Long.parseLong(tmp[4]);
                    records.add(createPosition(tmp, 5));
                } else {
                    records.add(createPosition(tmp, 0));
                }
            }
        }
        return new PositionData(records, crcValue, imeINumber, val);
    }

    private static Record createPosition(String[] line, int offset) {
        Date gpsDate = null;
        Date rtcDate = null;
        Date sentDate = null;
        double longitude;
        double latitude;
        int heading;
        short reportId;
        int odometer;
        BigDecimal gpsHop;
        short allInputStatus;
        short speed;
        short allOutputStatus;
        int analogInputValue;
        String driverId;
        String txtMessage = null;
        try {
            gpsDate = new Timestamp(Long.parseLong(line[offset].trim()) * 1000);
            rtcDate = new Timestamp(Long.parseLong(line[offset + 1].trim()) * 1000);
            sentDate = new Timestamp(Long.parseLong(line[offset + 2].trim()) * 1000);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        longitude = Double.parseDouble(line[offset + 3].trim()) / 1000000;
        latitude = Double.parseDouble(line[offset + 4].trim()) / 1000000;
        heading = Integer.parseInt(line[offset + 5].trim());
        reportId = (short) Byte.parseByte(line[offset + 6].trim());
        odometer = Integer.parseInt(line[offset + 7].trim());
        BigDecimal bigDecimal = new BigDecimal(line[offset + 8].trim());
        gpsHop = bigDecimal.divide(new BigDecimal(10), BigDecimal.ROUND_CEILING);
        allInputStatus = (short) Byte.parseByte(line[offset + 9].trim());
        speed = (short) Integer.parseInt(line[offset + 10].trim());
        allOutputStatus = (short) Byte.parseByte(line[offset + 11].trim());
        analogInputValue = Integer.parseInt(line[offset + 12].trim());
        if (line[offset + 13].equals("")) {
            driverId = "Unknown Driver";
        } else {
            driverId = line[offset + 13].trim();
        }
        return new Record(gpsDate, rtcDate, sentDate, longitude, latitude, heading, reportId, odometer, gpsHop,
                allInputStatus, speed, allOutputStatus, analogInputValue, driverId, txtMessage
        );
    }

    private static String bytesToUtf(byte[] data) {
        try {
            return new String(data, "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
