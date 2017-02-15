package com.yeahmobi.ssdb.client.command;

import com.yeahmobi.ssdb.client.Tuple;
import com.yeahmobi.ssdb.client.protocol.Status;

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
    Status set(String key, String value);

    Status setx(String key, String value, int seconds);

    Integer setnx(String key, String value);

    Integer expire(String key, int seconds);

    Integer ttl(String key);

    String get(String key);

    /**
     * @param key
     * @param value set的新值,如果不存在key,也会set value成功
     * @return 返回get的旧值, 如果不存在key, 则返回not_found, 请注意, 代码不要存储not_found的value否则你可能混淆
     */
    String getset(String key, String value);

    /**
     * @param key 删除的key
     * @return 返回状态码, 如果key不存在也会返回OK
     */
    Status del(String key);

    Long incr(String key);

    Long incr(String key, Long num);

    Boolean exists(String key);

    Integer getbit(String key, int offset);

    Integer setbit(String key, int offset, boolean isOne);

    /**
     * @param key
     * @param start =null则默认从0开始
     * @param end   =null默认最后一位 -1
     * @return
     */
    Long bitcount(String key, Long start, Long end);

    /**
     * 类似 bitcount ,但是表示的区间不一样
     * @param key
     * @param start
     * @param size 忽略掉size字节后计算,如果size=负数,则从末尾开始忽略
     * @return
     */
    Long countbit(String key, Long start, Long size);

    String substr(String key, Long start, Long size);

    Long strlen(String key);

    Set<String> keys(String keyStart, String keyEnd, int limit);

    Set<String> rkeys(String keyStart, String keyEnd, int limit);

    Map<String,String> scan(String keyStart, String keyEnd, int limit);

    Map<String,String> rscan(String keyStart, String keyEnd, int limit);

    Status multi_set(String... keyAndValue);

    Map<String,String> multi_get(String... key);

    Status multi_del(String... key);
    //#################hashmap#################
    Integer hset(String name, String key, String value);

    String hget(String name, String key);

    Integer hdel(String name, String key);

    Long hincr(String name, String key);

    Long hincr(String name, String key, Long num);

    Boolean hexists(String name, String key);

    Long hsize(String name);

    Set<String> hlist(String nameStart, String nameEnd, int limit);

    Set<String> hrlist(String nameStart, String nameEnd, int limit);

    Set<String> hkeys(String nameStart, String keyStart, String keyEnd, int limit);

    Map<String,String> hgetall(String name);

    Map<String,String> hscan(String name, String keyStart, String keyEnd, int limit);

    Map<String,String> hrscan(String name, String keyStart, String keyEnd, int limit);

    Long hclear(String name);

    Long multi_hset(String name, Map<String, String> keyValues);

    Long multi_hdel(String name, String... key);

    //################sortedSet#################
    Status zset(String name, String key, long score);

    Long zget(String name, String key);

    Status zdel(String name, String key);

    Boolean zexists(String name, String key);

    Long zsize(String name);

    Long zincr(String name, String key, long score);

    Set<Tuple> zrange(String name, int offset, int limit);

    Set<Tuple> zrrange(String name, int offset, int limit);

    Set<String> zlist(String nameStart, String nameEnd, int limit);

    Set<String> zrlist(String nameStart, String nameEnd, int limit);

    Set<String> zkeys(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit);

    /**
     * @param name
     * @param keyStart
     * @param scoreStart null=无限小
     * @param scoreEnd   null=无限大
     * @param limit      -1=全部
     * @return
     */
    Set<Tuple> zscan(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit);

    Set<Tuple> zrscan(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit);

    Long zclear(String name);

    Long zcount(String name, Long scoreStart, Long scoreEnd);

    Long zsum(String name, Long scoreStart, Long scoreEnd);

    Long zavg(String name, Long scoreStart, Long scoreEnd);

    Long zremrangebyscore(String name,Long scoreStart,Long scoreEnd);

    Long zpop_front(String name, int limit);

    Long zpop_back(String name, int limit);

    Long multi_zset(String name, String... keyAndScore);

    Set<Tuple> multi_zget(String name, String... key);

    Long multi_zdel(String name, String... key);

    /**
     * 谨慎使用,性能有问题
     * @param name
     * @param key
     * @return
     */
    Long zrank(String name, String key);

    Long zrrank(String name, String key);

    Long zremrangebyrank(String name, long start, long end);
}
