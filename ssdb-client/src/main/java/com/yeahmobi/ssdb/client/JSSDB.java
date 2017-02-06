package com.yeahmobi.ssdb.client;

import com.yeahmobi.ssdb.client.command.BaseCommand;
import com.yeahmobi.ssdb.client.command.SSDBCommand;
import com.yeahmobi.ssdb.client.connection.Connection;
import com.yeahmobi.ssdb.client.exception.JSSDBException;
import com.yeahmobi.ssdb.client.protocol.Protocol;
import com.yeahmobi.ssdb.client.protocol.Response;
import com.yeahmobi.ssdb.client.util.Pool;

import java.io.Closeable;
import java.io.IOException;
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
