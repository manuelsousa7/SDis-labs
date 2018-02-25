package example;

/** Auxiliary functions */
public class ByteUtil {

    /** Writes a byte array in hexadecimal text format */
    public static String writeByteArray(byte[] data) {
        int loc;
        int end;

        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int off = 0;

        // Print a hexadecimal dump of 'data[off...off+len-1]'
        if (off >= data.length)
            off = data.length;

        end = off+len;
        if (end >= data.length)
            end = data.length;

        len = end-off;
        if (len <= 0)
            return null;

        loc = (off/0x10)*0x10;

        for (int i = loc;  i < len;  i += 0x10, loc += 0x10) {
            int j = 0;

            for ( ;  j < 0x10  &&  i+j < end;  j++) {
                int ch;
                int d;

                if (j == 0x08)
                    sb.append(' ');

                ch = data[i+j] & 0xFF;

                d = (ch >>> 4);
                d = (d < 0xA ? d+'0' : d-0xA+'A');
                sb.append((char) d);

                d = (ch & 0x0F);
                d = (d < 0xA ? d+'0' : d-0xA+'A');
                sb.append((char) d);

                sb.append(' ');
            }
        }

        return "[ " + sb.toString() + "], length=" + sb.length()/3;
    }

}
