package com.yeahmobi.ssdbcluster.client.pressure;

import com.yeahmobi.ssdb.client.JSSDB;
import com.yeahmobi.ssdb.client.JSSDBPool;
import com.yeahmobi.ssdb.client.JSSDBPoolConfig;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/6 11:25
 * @desc 推荐用法,封装为单例,使用jdk7(try-with-resource)自动回收资源
 */
public class SSDBClient {

    static JSSDBPool jssdbPool;

    private static SSDBClient ssdbClient= new SSDBClient();

    static {
        JSSDBPoolConfig jssdbPoolConfig = new JSSDBPoolConfig();
        jssdbPoolConfig.setMaxTotal(100);
        jssdbPoolConfig.setMaxIdle(20);
        jssdbPool = new JSSDBPool(jssdbPoolConfig,"172.30.30.231",9801,3000);
    }

    private SSDBClient(){}

    public static SSDBClient getInstance(){
        if ( null ==  ssdbClient){
            ssdbClient = new SSDBClient();
        }
        return ssdbClient;
    }

    public void set(String key, String value){
        try(JSSDB jssdb = jssdbPool.getResource()){
            jssdb.set(key,value);
        }
    }

    public String get(String key){
        try(JSSDB jssdb = jssdbPool.getResource()){
            return jssdb.get(key);
        }
    }

}
