package com.yeahmobi.ssdb.client.protocol;

import com.yeahmobi.ssdb.client.exception.JSSDBDataException;
import com.yeahmobi.ssdb.client.exception.JSSDBException;

import java.io.UnsupportedEncodingException;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 13:39
 * @desc
 */
public class SafeEncoder {

    public static byte[][] encodeMany(final String... strs) {
        byte[][] many = new byte[strs.length][];
        for ( int i = 0; i < strs.length; i++ ) {
            many[i] = encode(strs[i]);
        }
        return many;
    }

    public static byte[] encode(final String str) {
        try {
            if ( str == null ) {
                throw new JSSDBDataException("value sent to redis cannot be null");
            }
            return str.getBytes(Protocol.CHARSET);
        } catch ( UnsupportedEncodingException e ) {
            throw new JSSDBException(e);
        }
    }

    public static String encode(final byte[] data) {
        try {
            return new String(data, Protocol.CHARSET);
        } catch ( UnsupportedEncodingException e ) {
            throw new JSSDBException(e);
        }
    }
}
