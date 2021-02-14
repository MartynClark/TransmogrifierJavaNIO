package examples;

import java.nio.*;

/**
 * User: martyn
 * Date: 21/01/2018
 * Time: 14:25
 */
public class ByteBufferExample {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte) 0xFE);
        buffer.put((byte) 0x02);
        buffer.putLong(12345678910245L);
        buffer.putShort((short) 59);
        buffer.flip();
        System.out.println(buffer.getShort(0));
        System.out.println(buffer.getLong(2));
        System.out.println(buffer.getShort(10));
//        for(int i = 0; i < buffer.limit(); i++){
//            System.out.println(buffer.get(i));
//        }

    }
}
