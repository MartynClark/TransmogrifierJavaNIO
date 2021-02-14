package examples;

/**
 * User: martyn
 * Date: 03/03/2013
 * Time: 21:26
 */
public class ByteWrapper {

    public static int getCrc(byte[] buffer) {
        return getCrc16(buffer, 0, buffer.length, 0xA001, 0);
    }

    /**
     * Calculates the CRC value for the given byte[] array of data
     * @param buffer byte[] data to be included in the calculation
     * @param offset start of data to be included
     * @param bufferLength length of data
     * @param polynom (0xA001) reversed
     * @param preset <code>Integer</code>
     * @return <code>Integer</code> value of calculated sum
     */
    public static int getCrc16(byte[] buffer, int offset, int bufferLength, int polynom, int preset) {
        preset &= 0xFFFF;
        polynom &= 0xFFFF;

        int crc = preset;
        for (int i = 0; i < bufferLength; i++) {
            int data = buffer[(i + offset) % buffer.length] & 0xFF;
            crc ^= data;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0) {
                    crc = (crc >> 1) ^ polynom;
                } else {
                    crc = crc >> 1;
                }
            }
        }
        return crc & 0xFFFF;
    }

}
