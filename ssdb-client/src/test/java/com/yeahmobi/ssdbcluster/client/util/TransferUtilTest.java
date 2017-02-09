package com.yeahmobi.ssdbcluster.client.util;

import com.yeahmobi.ssdb.client.util.TransferUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/8 18:53
 * @desc
 */
public class TransferUtilTest {

    @Test
    public void testMap2Array(){
        Map<String,String> map = new HashMap<>();
        map.put("a","1");
        map.put("b","2");
        map.put("a","2");
        String[] arr = TransferUtil.map2Array(map);
        System.out.println(arr);
    }

}
