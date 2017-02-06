package com.yeahmobi.ssdbcluster.client.client;

import com.yeahmobi.ssdb.client.JSSDB;
import com.yeahmobi.ssdb.client.JSSDBPool;
import com.yeahmobi.ssdb.client.Tuple;
import com.yeahmobi.ssdb.client.protocol.Response;
import org.junit.Before;
import org.junit.Test;

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
            Response r = ssdb.set("test111", "aaaabbb");
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
            Response r = ssdb.setx("test111","aabbsetx", 50);
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
    public void hset(){
        try(JSSDB ssdb = pool.getResource()){
            Integer result = ssdb.hset("name111","test111","abchset");
            System.out.println(result);
        }
    }

    @Test
    public void zset(){
        try(JSSDB ssdb = pool.getResource()){
            Response result = ssdb.zset("name222","test111",100);
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
            System.out.println(result);
        }
    }

}
