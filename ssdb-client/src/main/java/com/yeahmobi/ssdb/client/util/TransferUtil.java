package com.yeahmobi.ssdb.client.util;

import java.util.Map;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/8 18:48
 * @desc
 */
public class TransferUtil {

    public static String[] map2Array(Map<String, String> keyValues) {
        if ( null == keyValues || keyValues.size() == 0 ) {
            return new String[] {};
        }
        String[] keyValueArray = new String[keyValues.size() * 2];
        int index = 0;
        for ( Map.Entry<String, String> entry : keyValues.entrySet() ) {
            keyValueArray[index++] = entry.getKey();
            keyValueArray[index++] = entry.getValue();
        }
        return keyValueArray;
    }

    public static String[] map2Array(String insertFirstValue, Map<String, String> keyValues) {
        if ( null == insertFirstValue ){
            return map2Array(keyValues);
        }

        if ( null == keyValues || keyValues.size() == 0 ) {
            return new String[] { insertFirstValue };
        }
        String[] keyValueArray = new String[keyValues.size() * 2 + 1];
        keyValueArray[0] = insertFirstValue;
        int index = 1;
        for ( Map.Entry<String, String> entry : keyValues.entrySet() ) {
            keyValueArray[index++] = entry.getKey();
            keyValueArray[index++] = entry.getValue();
        }
        return keyValueArray;
    }

}
