package com.yeahmobi.ssdb.client.protocol;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 12:58
 * @desc
 */
public final class Protocol {

    public static final String DEFAULT_HOST = "localhost";

    public static final int DEFAULT_PORT = 8888;

    public static final int DEFAULT_TIMEOUT = 5000;

    public static final int BUFFER_SIZE = 4096;

    public static final String CHARSET = "UTF-8";

    private Protocol() {
    }

    public static enum Command {
        SET, SETX, SETNX, EXPIRE, TTL, GET, GETSET, DEL, INCR, EXISTS, GETBIT, SETBIT, BITCOUNT, COUNTBIT, SUBSTR, STRLEN, KEYS, RKEYS, SCAN, RSCAN,
        MULTI_SET, MULTI_GET, MULTI_DEL,//String
        HSET, HGET, MULTI_HSET, HGETALL, MULTI_HDEL, HDEL, HINCR, HEXISTS, HSIZE, HLIST, HRLIST, HKEYS, HSCAN, HRSCAN, HCLEAR,//hashmap
        ZSET, ZINCR, ZGET, ZRANGE, ZRRANGE, ZLIST, ZSCAN, ZRSCAN, ZDEL, ZEXISTS, ZSIZE, ZRLIST, ZKEYS, ZCLEAR, ZCOUNT, ZSUM, ZAVG, ZREMRANGEBYSCORE,
        ZPOP_FRONT,ZPOP_BACK,MULTI_ZSET,MULTI_ZGET,MULTI_ZDEL,ZRANK,ZRRANK,ZREMRANGEBYRANK,//sortedset
        AUTH, DBSIZE, INFO;//server

        public final byte[] raw;

        public final String name;

        Command() {
            raw = SafeEncoder.encode(this.name().toLowerCase());
            name = name().toLowerCase();
        }
    }

}
