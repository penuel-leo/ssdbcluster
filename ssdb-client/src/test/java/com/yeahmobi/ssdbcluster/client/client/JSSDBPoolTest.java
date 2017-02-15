package com.yeahmobi.ssdbcluster.client.client;

import com.alibaba.fastjson.JSONObject;
import com.yeahmobi.ssdb.client.JSSDB;
import com.yeahmobi.ssdb.client.JSSDBPool;
import com.yeahmobi.ssdb.client.Tuple;
import com.yeahmobi.ssdb.client.protocol.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 17:49
 * @desc
 */
public class JSSDBPoolTest {

    private JSSDBPool pool;

    @Before
    public void init(){
        pool = new JSSDBPool("172.30.30.231", 9801);
    }

    @Test
    public void set(){
        try(JSSDB ssdb = pool.getResource()){
            Status r = ssdb.set("test111", "aaaabbb");
            System.out.println(r);
        }
    }

    @Test
    public void get(){
        try(JSSDB ssdb = pool.getResource()){
            String result = ssdb.get("test111");
            System.out.println(result);
        }
    }

    @Test
    public void setx(){
        try(JSSDB ssdb = pool.getResource()){
            Status r = ssdb.setx("test111","aabbsetx", 50);
            System.out.println(r);
        }
    }

    @Test
    public void setnx(){
        try(JSSDB ssdb = pool.getResource()){
            Integer r = ssdb.setnx("test111", "abcsetnx");
            System.out.println(r);
        }
    }

    @Test
    public void expire(){
        try(JSSDB ssdb = pool.getResource()){
            Integer result = ssdb.expire("test111",100);
            System.out.println(result);
        }
    }

    @Test
    public void ttl(){
        try(JSSDB ssdb = pool.getResource()){
            Integer result = ssdb.ttl("test111");
            System.out.println(result);
        }
    }

    @Test
    public void zset(){
        try(JSSDB ssdb = pool.getResource()){
            Status result = ssdb.zset("name222","test111",100);
            System.out.println(result);
        }
    }

    @Test
    public void zget(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.zget("name222","test111");
            System.out.println(result);
        }
    }

    @Test
    public void zincr(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.zincr("name222","test111",22);
            System.out.println(result);
        }
    }

    @Test
    public void zrange(){
        try(JSSDB ssdb = pool.getResource()){
            Set<Tuple> result = ssdb.zrange("name222", 0, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zrrange(){
        try(JSSDB ssdb = pool.getResource()){
            Set<Tuple> result = ssdb.zrrange("name222", 0, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zlist(){
        try(JSSDB ssdb = pool.getResource()){
            Set<String> result = ssdb.zlist(null, null, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zscan(){
        try(JSSDB ssdb = pool.getResource()){
            Set<Tuple> result = ssdb.zscan("zset", null, null, null, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zrscan(){
        try(JSSDB ssdb = pool.getResource()){
            Set<Tuple> result = ssdb.zrscan("zset", null, null, null, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zdel(){
        try(JSSDB ssdb = pool.getResource()){
            Status result = ssdb.zdel("zset", "g");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zexists(){
        try(JSSDB ssdb = pool.getResource()){
            Boolean result = ssdb.zexists("zset", "b");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void zsize(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.zsize("zset");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void getset(){
        try(JSSDB ssdb = pool.getResource()){
            String result = ssdb.getset("b", "222");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void del(){
        try(JSSDB ssdb = pool.getResource()){
            Status result = ssdb.del("b");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void incr(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.incr("a", 10L);
            result = ssdb.incr("a");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void exists(){
        try(JSSDB ssdb = pool.getResource()){
            Boolean result = ssdb.exists("c");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void getbit(){
        try(JSSDB ssdb = pool.getResource()){
            Integer result = ssdb.getbit("a", 1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void setbit(){
        try(JSSDB ssdb = pool.getResource()){
            Integer result = ssdb.setbit("b", 2, false);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void bitcount(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.bitcount("b", 2L, 10L);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void substr(){
        try(JSSDB ssdb = pool.getResource()){
            String result = ssdb.substr("a", 0L, 2L);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void keys(){
        try(JSSDB ssdb = pool.getResource()){
            Set<String> result = ssdb.keys(null, null, 10);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void scan(){
        try(JSSDB ssdb = pool.getResource()){
            Map<String, String> result = ssdb.scan(null, null, 10);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void multi_set(){
        try(JSSDB ssdb = pool.getResource()){
            Status result = ssdb.multi_set("b", "123", "c", "222");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void multi_get(){
        try(JSSDB ssdb = pool.getResource()){
            Map<String, String> result = ssdb.multi_get("a", "b", "f");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void multi_del(){
        try(JSSDB ssdb = pool.getResource()){
            Status result = ssdb.multi_del("c", "e");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void hset(){
        try(JSSDB ssdb = pool.getResource()){
            Integer result = ssdb.hset("hset","a","111");
            System.out.println(result);
        }
    }

    @Test
    public void multi_hset(){
        Map<String,String> kvs = new HashMap<>();
        kvs.put("b","222");
        kvs.put("c","333");
        try(JSSDB jssdb = pool.getResource()){
            Long result = jssdb.multi_hset("hset", kvs);
            System.out.println(result);
        }
    }

    @Test
    public void hget(){
        try(JSSDB ssdb = pool.getResource()){
            String result = ssdb.hget("hset","a");
            System.out.println(result);
        }
    }

    @Test
    public void hgetAll(){
        try(JSSDB ssdb = pool.getResource()){
            Map<String,String> result = ssdb.hgetall("hset");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void multi_hdel(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.multi_hdel("hset", "test111","test4");
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void hincr(){
        try(JSSDB ssdb = pool.getResource()){
            Long result = ssdb.hincr("hset", "a", 100L);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void hlist(){
        try(JSSDB ssdb = pool.getResource()){
            Set<String> result = ssdb.hrlist("", "", -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void hscan(){
        try(JSSDB ssdb = pool.getResource()){
            Map<String, String> result = ssdb.hscan("hset", null,null, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void hkeys(){
        try(JSSDB ssdb = pool.getResource()){
            Set<String> result = ssdb.hkeys("hset", null, null, -1);
            System.out.println(JSONObject.toJSONString(result));
        }
    }

    @Test
    public void info(){
        try(JSSDB ssdb = pool.getResource()){
            String result = ssdb.info();
            System.out.println(result);
        }
    }

}
