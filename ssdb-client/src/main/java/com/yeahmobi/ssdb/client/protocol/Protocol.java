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
        SET, SETX, SETNX, EXPIRE, TTL, GET,//String
        HSET,//hashmap
        ZSET, ZINCR, ZGET, ZRANGE,//sortedset
        AUTH;//server

        public final byte[] raw;

        public final String name;

        Command() {
            raw = SafeEncoder.encode(this.name().toLowerCase());
            name = name().toLowerCase();
        }
    }

}
