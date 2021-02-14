package handlers;

import examples.*;
import util.*;

import java.io.*;
import java.net.*;

/**
 * User: martyn
 * Date: 27/10/2017
 * Time: 15:00
 */
public class TransmogrifyHandler implements Handler<Socket> {

    @Override
    public void handle(Socket socket) throws IOException {

        try (socket; DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            //            int data;
            // read keep-alive details (always sent in binary)
            in.readShort(); // 2 bytes position 0
            final long IMEI_NUMBER = in.readLong(); // imei number 8 bytes position 2
            System.out.println("Connecting Vehicle : " + IMEI_NUMBER);
            final short SEQUENCE_ID = in.readShort(); // 2 bytes position 10
            // send first reply
            System.out.println("SENDING FIRST ACK REPLY");
            out.write(Util.prepareACK(IMEI_NUMBER, SEQUENCE_ID).array());
            out.flush();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(buffer, 0, bytesRead);
                byte[] data = baos.toByteArray();
                PositionData positionData = PositionDataDecoder.decodePacket(data);
                int calculatedCrcValue = ByteWrapper.getCrc(Util.copyRange(data, 7, data.length));
                if (!(positionData.getCRCValue() == calculatedCrcValue)) {
                    System.out.println("DROPPING PACKET CRC CHECK FAILED");
                } else {
                    System.out.println(new String(data));
                    System.out.println("SENDING SECOND ACK REPLY ");
                    out.write(Util.prepareOtherACK(positionData.getImeiNumber(), positionData.getSeqID()).array
                            ());
                    out.flush();
                }
                System.out.println("Waiting for more data: ");
            }
        }
//        try (s; InputStream in = s.getInputStream(); OutputStream out = s.getOutputStream()) {
//            int data;
//            while ((data = in.read()) != -1) {
//                out.write(Util.transmogrify(data));
//            }
//        }
    }
}
