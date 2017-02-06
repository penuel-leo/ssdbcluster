package com.yeahmobi.ssdb.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 15:38
 * @desc
 */
public class JSSDBPoolConfig extends GenericObjectPoolConfig{

    public JSSDBPoolConfig() {
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }

}
