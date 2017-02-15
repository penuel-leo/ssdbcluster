package com.yeahmobi.ssdbcluster.client.client;

import com.yeahmobi.ssdb.client.JSSDB;
import com.yeahmobi.ssdb.client.JSSDBPool;
import com.yeahmobi.ssdb.client.JSSDBPoolConfig;
import com.yeahmobi.ssdb.client.util.ConfigUtil;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/15 13:42
 * @desc
 */
public class JSSDBClientDemo {

    static JSSDBPool jssdbPool;

    private static JSSDBClientDemo ssdbClient = new JSSDBClientDemo();

    static {
        JSSDBPoolConfig jssdbPoolConfig = new JSSDBPoolConfig();
        jssdbPoolConfig.setMaxTotal(50);
        jssdbPoolConfig.setMaxIdle(20);
        String ssdbHost = ConfigUtil.getDefaultConfig().getProperty("ssdb.ip", "172.30.30.231");
        int ssdbPort = Integer.valueOf(ConfigUtil.getDefaultConfig().getProperty("ssdb.port", "9801"));
        jssdbPool = new JSSDBPool(jssdbPoolConfig, ssdbHost, ssdbPort, 3000);
    }

    private JSSDBClientDemo() {
    }

    public static JSSDBClientDemo getInstance() {
        if ( null == ssdbClient ) {
            ssdbClient = new JSSDBClientDemo();
        }
        return ssdbClient;
    }

    public Integer hset(String name, String key, String value) {
        try ( JSSDB jssdb = jssdbPool.getResource() ) {
            return jssdb.hset(name, key, value);
        }
    }

}
