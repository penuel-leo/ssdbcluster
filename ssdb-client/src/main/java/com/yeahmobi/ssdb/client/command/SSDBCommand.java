package com.yeahmobi.ssdb.client.command;

import com.yeahmobi.ssdb.client.Tuple;
import com.yeahmobi.ssdb.client.protocol.Response;
import com.yeahmobi.ssdb.client.protocol.Response.Status;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 16:06
 * @desc
 */
public interface SSDBCommand {

    //#############key,value##################
    Response set(String key, String value);

    Response setx(String key, String value, int seconds);

    Integer setnx(String key, String value);

    Integer expire(String key, int seconds);

    Integer ttl(String key);

    String get(String key);


    //#################hashmap#################
    Integer hset(String name, String key, String value);

    Long multi_hset(String name, Map<String,String> keyValues);

    String hget(String name, String key);

    Map<String,String> hgetAll(String name);

    //################sortedSet#################
    Response zset(String name,String key, long score);

    Long zget(String name, String key);

    Long zincr(String name,String key, long score);

    Set<Tuple> zrange(String name, int offset, int limit);

    List<String> zlist(String nameStart,String nameEnd,int limit);

    /**
     *
     * @param name
     * @param keyStart
     * @param scoreStart null=无限小
     * @param scoreEnd null=无限大
     * @param limit -1=全部
     * @return
     */
    Set<Tuple> zscan(String name, String keyStart, Long scoreStart,Long scoreEnd,int limit);

}
