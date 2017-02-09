package com.yeahmobi.ssdb.client;

import com.yeahmobi.ssdb.client.command.BaseCommand;
import com.yeahmobi.ssdb.client.command.SSDBCommand;
import com.yeahmobi.ssdb.client.connection.Connection;
import com.yeahmobi.ssdb.client.exception.JSSDBException;
import com.yeahmobi.ssdb.client.protocol.Protocol;
import com.yeahmobi.ssdb.client.protocol.Response;
import com.yeahmobi.ssdb.client.util.Pool;
import com.yeahmobi.ssdb.client.util.TransferUtil;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
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
    public Response set(String key, String value) {
        connection.sendCommand(Protocol.Command.SET, key, value);
        return connection.getResponse();
    }

    @Override
    public Response setx(String key, String value, int seconds) {
        connection.sendCommand(Protocol.Command.SETX, key, value, String.valueOf(seconds));
        return connection.getResponse();
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
    public String hget(String name, String key) {
        connection.sendCommand(Protocol.Command.HGET, name, key);
        return connection.getResponse().getStringContent();
    }

    @Override
    public Set<Tuple> hgetAll(String name) {
        connection.sendCommand(Protocol.Command.HGETALL, name);
        return connection.getResponse().getTupleContent();
    }


    @Override
    public Response zset(String name, String key, long score) {
        connection.sendCommand(Protocol.Command.ZSET, name, key, String.valueOf(score));
        return connection.getResponse();
    }

    @Override
    public Long zget(String name, String key) {
        connection.sendCommand(Protocol.Command.ZGET, name, key);
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
    public List<String> zlist(String nameStart, String nameEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZLIST, null == nameStart ? "" : nameStart, null == nameEnd ? "" : nameEnd, String.valueOf(limit));
        return connection.getResponse().getListContent();
    }

    @Override
    public Set<Tuple> zscan(String name, String keyStart, Long scoreStart, Long scoreEnd, int limit) {
        connection.sendCommand(Protocol.Command.ZSCAN, name, null == keyStart ? "" : keyStart, null == scoreStart ? "" : String.valueOf(scoreStart),
                               null == scoreEnd ? "" : String.valueOf(scoreEnd), String.valueOf(limit));
        return connection.getResponse().getTupleContent();
    }

    @Override
    public Response.Status auth(String password) {
        connection.sendCommand(Protocol.Command.AUTH, password);
        return connection.getResponse().getStatus();
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
