package com.yeahmobi.ssdb.client;

import com.yeahmobi.ssdb.client.command.BaseCommand;
import com.yeahmobi.ssdb.client.command.SSDBCommand;
import com.yeahmobi.ssdb.client.connection.Connection;
import com.yeahmobi.ssdb.client.exception.JSSDBException;
import com.yeahmobi.ssdb.client.protocol.Protocol;
import com.yeahmobi.ssdb.client.protocol.Response;
import com.yeahmobi.ssdb.client.protocol.Status;
import com.yeahmobi.ssdb.client.util.Pool;
import com.yeahmobi.ssdb.client.util.TransferUtil;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 12:56
 * @desc
 */
public class JSSDB implements SSDBCommand, BaseCommand, Closeable {

    protected Connection connection = null;

    protected Pool<JSSDB> dataSource = null;

    public JSSDB() {
        connection = new Connection();
    }

    public JSSDB(final String host, final int port) {
        connection = new Connection(host, port);
    }

    public JSSDB(final String host, final int port, final int timeout) {
        connection = new Connection(host, port, timeout, timeout);
    }

    public JSSDB(final String host, final int port, final int connectionTimeout, final int soTimeout) {
        connection = new Connection(host, port, connectionTimeout, soTimeout);
    }

    public JSSDB(final String host, final int port, final int connectionTimeout, final int soTimeout, final int bufferSize) {
        connection = new Connection(host, port, connectionTimeout, soTimeout, bufferSize);
    }

    @Override
    public Status set(String key, String value) {
        connection.sendCommand(Protocol.Command.SET, key, value);
        return connection.getResponse().getStatus();
    }

    @Override
    public Status setx(String key, String value, int seconds) {
        connection.sendCommand(Protocol.Command.SETX, key, value, String.valueOf(seconds));
        return connection.getResponse().getStatus();
    }

    @Override
    public Integer setnx(String key, String value) {
        connection.sendCommand(Protocol.Command.SETNX, key, value);
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public Integer expire(String key, int seconds) {
        connection.sendCommand(Protocol.Command.EXPIRE, key, String.valueOf(seconds));
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public Integer ttl(String key) {
        connection.sendCommand(Protocol.Command.TTL, key);
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public String get(String key) {
        connection.sendCommand(Protocol.Command.GET, key);
        Response r = connection.getResponse();
        return r.getStringContent();
    }

    @Override
    public String getset(String key, String value) {
        connection.sendCommand(Protocol.Command.GETSET, key, value);
        return connection.getResponse().getStringContent();
    }

    @Override
    public Status del(String key) {
        connection.sendCommand(Protocol.Command.DEL, key);
        return connection.getResponse().getStatus();
    }

    @Override
    public Long incr(String key) {
        connection.sendCommand(Protocol.Command.INCR, key);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long incr(String key, Long num) {
        connection.sendCommand(Protocol.Command.INCR, key, num == null ? "1" : String.valueOf(num));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Boolean exists(String key) {
        connection.sendCommand(Protocol.Command.EXISTS, key);
        return connection.getResponse().getLongContent() == 1;
    }

    @Override
    public Integer getbit(String key, int offset) {
        connection.sendCommand(Protocol.Command.GETBIT, key, String.valueOf(offset));
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public Integer setbit(String key, int offset, boolean isOne) {
        connection.sendCommand(Protocol.Command.SETBIT, key, String.valueOf(offset), isOne ? "1" : "0");
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public Long bitcount(String key, Long start, Long end) {
        connection.sendCommand(Protocol.Command.BITCOUNT, key, null == start ? "" : String.valueOf(start), null == end ? null : String.valueOf(end));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long countbit(String key, Long start, Long size) {
        connection.sendCommand(Protocol.Command.COUNTBIT, key, null == start ? "" : String.valueOf(start), null == size ? null : String.valueOf(size));
        return connection.getResponse().getLongContent();
    }

    @Override
    public String substr(String key, Long start, Long size) {
        connection.sendCommand(Protocol.Command.SUBSTR, key, null == start ? "" : String.valueOf(start), null == size ? null : String.valueOf(size));
        return connection.getResponse().getStringContent();
    }

    @Override
    public Long strlen(String key) {
        connection.sendCommand(Protocol.Command.STRLEN, key);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Set<String> keys(String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.KEYS, null == keyStart ? "" : keyStart, null == keyEnd ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Set<String> rkeys(String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.RKEYS, null == keyStart ? "" : keyStart, null == keyEnd ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Map<String, String> scan(String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.SCAN, null == keyStart ? "" : keyStart, null == keyEnd ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getMapContent();
    }

    @Override
    public Map<String, String> rscan(String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.RSCAN, null == keyStart ? "" : keyStart, null == keyEnd ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getMapContent();
    }

    @Override
    public Status multi_set(String... keyAndValue) {
        connection.sendCommand(Protocol.Command.MULTI_SET, keyAndValue);
        return connection.getResponse().getStatus();
    }

    @Override
    public Map<String, String> multi_get(String... key) {
        connection.sendCommand(Protocol.Command.MULTI_GET, key);
        return connection.getResponse().getMapContent();
    }

    @Override
    public Status multi_del(String... key) {
        connection.sendCommand(Protocol.Command.MULTI_DEL, key);
        return connection.getResponse().getStatus();
    }

    @Override
    public Integer hset(String name, String key, String value) {
        connection.sendCommand(Protocol.Command.HSET, name, key, value);
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public Long multi_hset(String name, Map<String, String> keyValues) {
        connection.sendCommand(Protocol.Command.MULTI_HSET, TransferUtil.map2Array(name, keyValues));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long multi_hdel(String name, String... key) {
        connection.sendCommand(Protocol.Command.MULTI_HDEL, TransferUtil.compose2Array(name, key));
        return connection.getResponse().getLongContent();
    }

    @Override
    public String hget(String name, String key) {
        connection.sendCommand(Protocol.Command.HGET, name, key);
        return connection.getResponse().getStringContent();
    }

    @Override
    public Integer hdel(String name, String key) {
        connection.sendCommand(Protocol.Command.HDEL, name, key);
        return connection.getResponse().getIntegerContent();
    }

    @Override
    public Long hincr(String name, String key) {
        connection.sendCommand(Protocol.Command.HINCR, name, key);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long hincr(String name, String key, Long num) {
        connection.sendCommand(Protocol.Command.HINCR, name, key, null == num ? "1" : String.valueOf(num));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Boolean hexists(String name, String key) {
        connection.sendCommand(Protocol.Command.HEXISTS, name, key);
        return connection.getResponse().getLongContent() == 1;
    }

    @Override
    public Long hsize(String name) {
        connection.sendCommand(Protocol.Command.HSIZE, name);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Set<String> hlist(String nameStart, String nameEnd, int limit) {
        connection.sendCommand(Protocol.Command.HLIST, nameStart == null ? "" : nameStart, nameEnd == null ? "" : nameEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Set<String> hrlist(String nameStart, String nameEnd, int limit) {
        connection.sendCommand(Protocol.Command.HRLIST, nameStart == null ? "" : nameStart, nameEnd == null ? "" : nameEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Set<String> hkeys(String name, String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.HKEYS, name, keyStart == null ? "" : keyStart, keyEnd == null ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Map<String, String> hgetall(String name) {
        connection.sendCommand(Protocol.Command.HGETALL, name);
        return connection.getResponse().getMapContent();
    }

    @Override
    public Map<String, String> hscan(String name, String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.HSCAN, name, keyStart == null ? "" : keyStart, keyEnd == null ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getMapContent();
    }

    @Override
    public Map<String, String> hrscan(String name, String keyStart, String keyEnd, int limit) {
        connection.sendCommand(Protocol.Command.HRSCAN, name, keyStart == null ? "" : keyStart, keyEnd == null ? "" : keyEnd, String.valueOf(limit));
        return connection.getResponse().getMapContent();
    }

    @Override
    public Long hclear(String name) {
        connection.sendCommand(Protocol.Command.HCLEAR, name);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Status zset(String name, String key, long score) {
        connection.sendCommand(Protocol.Command.ZSET, name, key, String.valueOf(score));
        return connection.getResponse().getStatus();
    }

    @Override
    public Long zget(String name, String key) {
        connection.sendCommand(Protocol.Command.ZGET, name, key);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Status zdel(String name, String key) {
        connection.sendCommand(Protocol.Command.ZDEL, name, key);
        return connection.getResponse().getStatus();
    }

    @Override
    public Boolean zexists(String name, String key) {
        connection.sendCommand(Protocol.Command.ZEXISTS, name, key);
        return connection.getResponse().getLongContent() == 1;
    }

    @Override
    public Long zsize(String name) {
        connection.sendCommand(Protocol.Command.ZSIZE, name);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zincr(String name, String key, long score) {
        connection.sendCommand(Protocol.Command.ZINCR, name, key, String.valueOf(score));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Set<Tuple> zrange(String name, int offset, int limit) {
        connection.sendCommand(Protocol.Command.ZRANGE, name, String.valueOf(offset), String.valueOf(limit));
        return connection.getResponse().getTupleContent();
    }

    @Override
    public Set<Tuple> zrrange(String name, int offset, int limit) {
        connection.sendCommand(Protocol.Command.ZRRANGE, name, String.valueOf(offset), String.valueOf(limit));
        return connection.getResponse().getTupleContent();
    }

    @Override
    public Set<String> zlist(String nameStart, String nameEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZLIST, null == nameStart ? "" : nameStart, null == nameEnd ? "" : nameEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Set<String> zrlist(String nameStart, String nameEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZRLIST, nameStart, nameEnd, String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Set<String> zkeys(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZKEYS, name, keyStart == null ? "" : keyStart, scoreStart == null ? "" : String.valueOf(scoreStart),
            scoreEnd == null ? "" : String.valueOf(scoreEnd), String.valueOf(limit));
        return connection.getResponse().getSetContent();
    }

    @Override
    public Set<Tuple> zscan(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZSCAN, name, null == keyStart ? "" : keyStart, null == scoreStart ? "" : String.valueOf(scoreStart),
            null == scoreEnd ? "" : String.valueOf(scoreEnd), String.valueOf(limit));
        return connection.getResponse().getTupleContent();
    }

    @Override
    public Set<Tuple> zrscan(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZRSCAN, name, null == keyStart ? "" : keyStart, null == scoreStart ? "" : String.valueOf(scoreStart),
            null == scoreEnd ? "" : String.valueOf(scoreEnd), String.valueOf(limit));
        return connection.getResponse().getTupleContent();
    }

    @Override
    public Long zclear(String name) {
        connection.sendCommand(Protocol.Command.ZCLEAR, name);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zcount(String name, Long scoreStart, Long scoreEnd) {
        connection.sendCommand(Protocol.Command.ZCOUNT, name, null == scoreStart ? "" : String.valueOf(scoreStart),
            null == scoreEnd ? "" : String.valueOf(scoreEnd));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zsum(String name, Long scoreStart, Long scoreEnd) {
        connection.sendCommand(Protocol.Command.ZSUM, name, null == scoreStart ? "" : String.valueOf(scoreStart),
            null == scoreEnd ? "" : String.valueOf(scoreEnd));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zavg(String name, Long scoreStart, Long scoreEnd) {
        connection.sendCommand(Protocol.Command.ZAVG, name, null == scoreStart ? "" : String.valueOf(scoreStart),
            null == scoreEnd ? "" : String.valueOf(scoreEnd));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zremrangebyscore(String name, Long scoreStart, Long scoreEnd) {
        connection.sendCommand(Protocol.Command.ZREMRANGEBYSCORE, name, null == scoreStart ? "" : String.valueOf(scoreStart),
            null == scoreEnd ? "" : String.valueOf(scoreEnd));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zpop_front(String name, int limit) {
        connection.sendCommand(Protocol.Command.ZPOP_FRONT, name, String.valueOf(limit));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zpop_back(String name, int limit) {
        connection.sendCommand(Protocol.Command.ZPOP_BACK, name, String.valueOf(limit));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long multi_zset(String name, String... keyAndScore) {
        connection.sendCommand(Protocol.Command.MULTI_ZSET, TransferUtil.compose2Array(name, keyAndScore));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Set<Tuple> multi_zget(String name, String... key) {
        connection.sendCommand(Protocol.Command.MULTI_ZGET, TransferUtil.compose2Array(name, key));
        return connection.getResponse().getTupleContent();
    }

    @Override
    public Long multi_zdel(String name, String... key) {
        connection.sendCommand(Protocol.Command.MULTI_ZDEL, TransferUtil.compose2Array(name, key));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zrank(String name, String key) {
        connection.sendCommand(Protocol.Command.ZRANK, name, key);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zrrank(String name, String key) {
        connection.sendCommand(Protocol.Command.ZRRANK, name, key);
        return connection.getResponse().getLongContent();
    }

    @Override
    public Long zremrangebyrank(String name, long start, long end) {
        connection.sendCommand(Protocol.Command.ZREMRANGEBYRANK, name, String.valueOf(start), String.valueOf(end));
        return connection.getResponse().getLongContent();
    }

    @Override
    public Status auth(String password) {
        connection.sendCommand(Protocol.Command.AUTH, password);
        return connection.getResponse().getStatus();
    }

    @Override
    public Long dbsize() {
        connection.sendCommand(Protocol.Command.DBSIZE);
        return connection.getResponse().getLongContent();
    }

    @Override
    public String info() {
        connection.sendCommand(Protocol.Command.INFO);
        return connection.getResponse().getStringContent();
    }

    @Override
    public void close() {
        if ( dataSource != null ) {
            if ( connection.isBroken() ) {
                this.dataSource.returnBrokenResource(this);
            } else {
                this.dataSource.returnResource(this);
            }
        } else {
            try {
                connection.close();
            } catch ( IOException e ) {
                throw new JSSDBException("can not close jssdb connection", e);
            }
        }
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    public void setDataSource(Pool<JSSDB> dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

}
