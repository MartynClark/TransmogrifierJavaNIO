package util;

import examples.*;

import java.io.*;
import java.nio.*;
import java.util.*;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 14:52
 */
public class Util {

    public static int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }

    public static void transmogrify(ByteBuffer buf) {
        System.out.println("Transmogrification done by " + Thread.currentThread());
        // pos = 0, limit = 80, capacity = 80
        // "hello\n" pos = 6, limit = 80, capacity = 80
        buf.flip();
        // "hello\n" pos = 0, limit = 6, capacity = 80
        short fixedHeader = buf.getShort(0);
        long imeiNumber = buf.getLong(2);
        short seqId = buf.getShort(10);
        System.out.println("Header " + fixedHeader + " imei" + imeiNumber + " seqId " + seqId);
        for (int i = 0; i < buf.limit(); i++) {
            System.out.println(buf.get(i));
            buf.put(Util.prepareACK(imeiNumber, seqId));
            //            buf.put(i, (byte) transmogrify(buf.get(i)));
        }
    }

    public static void process(ByteBuffer buf) {
        System.out.println("PROCESSING");
        if (buf.position() == 12) {
            short fixedHeader = buf.getShort(0);
            final long IMEI_NUMBER = buf.getLong(2);
            final short SEQUENCE_ID = buf.getShort(10);
            System.out.println("Header " + fixedHeader + " imeiNumber  " + IMEI_NUMBER + " seq " + SEQUENCE_ID);
            System.out.println("sending first ack reply.");
            buf.flip();
            final byte BYTE_ONE = (byte) 0xFE;
            final byte BYTE_TWO = (byte) 0x02;
            buf.put(BYTE_ONE); // 1 byte
            buf.put(BYTE_TWO); // 1 byte
            buf.putLong(IMEI_NUMBER); // 8 bytes
            buf.putShort(SEQUENCE_ID); // 2 bytes

        } else {
            try {
                System.out.println(new String(buf.array(), "UTF-8").trim());
                PositionData positionData = PositionDataDecoder.decodePacket(buf.array());
                buf.flip();
                final byte BYTE_ONE = (byte) 0xFE;
                final byte BYTE_TWO = (byte) 0x02;
                buf.put(BYTE_ONE); // 1 byte
                buf.put(BYTE_TWO); // 1 byte
                buf.putLong(positionData.getImeiNumber()); // 8 bytes
                buf.putChar(positionData.getSeqID()); // 2 bytes
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //           buf.flip();
        //        PositionData positionData = PositionDataDecoder.decodePacket(buf.array());
        //        System.out.println( positionData);
        //        buf.flip();
        //        buf.put(BYTE_ONE); // 1 byte
        //        buf.put(BYTE_TWO); // 1 byte
        //        buf.putLong(positionData.getImeiNumber()); // 8 bytes
        //        buf.putChar(positionData.getSeqID()); // 2 bytes
        //        buf.flip();
        //        final byte BYTE_ONE = (byte) 0xFE;
        //        final byte BYTE_TWO = (byte) 0x02;
        //        buf.put(BYTE_ONE); // 1 byte
        //        buf.put(BYTE_TWO); // 1 byte
        //        buf.putLong(IMEI_NUMBER); // 8 bytes
        //        buf.putShort(SEQUENCE_ID); // 2 bytes
    }

    public static ByteBuffer prepareACK(long IMEI_NUMBER, short SEQUENCE_ID) {
        final byte BYTE_ONE = (byte) 0xFE;
        final byte BYTE_TWO = (byte) 0x02;
        ByteBuffer byteBuffer = ByteBuffer.allocate(12); // Total 12 bytes
        byteBuffer.put(BYTE_ONE); // 1 byte
        byteBuffer.put(BYTE_TWO); // 1 byte
        byteBuffer.putLong(IMEI_NUMBER); // 8 bytes
        byteBuffer.putShort(SEQUENCE_ID); // 2 bytes
        // total 12 bytes
        return byteBuffer;
    }

    public static ByteBuffer prepareOtherACK(long IMEI_NUMBER, char SEQUENCE_ID) {
        final byte BYTE_ONE = (byte) 0xFE;
        final byte BYTE_TWO = (byte) 0x02;
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.put(BYTE_ONE); // 1 byte
        byteBuffer.put(BYTE_TWO); // 1 byte
        byteBuffer.putLong(IMEI_NUMBER); // 8 bytes
        byteBuffer.putChar(SEQUENCE_ID); // 2 bytes
        return byteBuffer; // total 12 bytes
    }

    /**
     * Utility method for returning a range from with in an array
     *
     * @param array the data to work on
     * @param from  int start of range (0) based
     * @param to    int end of range
     * @return byte[] selected range
     */
    public static byte[] copyRange(byte[] array, int from, int to) {
        return Arrays.copyOfRange(array, from, to);
    }
}
