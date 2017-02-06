package com.yeahmobi.ssdb.client;

import com.yeahmobi.ssdb.client.exception.JSSDBException;
import com.yeahmobi.ssdb.client.protocol.Protocol;
import com.yeahmobi.ssdb.client.util.Pool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 12:56
 * @desc
 */
public class JSSDBPool extends Pool<JSSDB> {

    public JSSDBPool() {
        this(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public JSSDBPool(final String host, final int port) {
        this(new GenericObjectPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT);
    }

    public JSSDBPool(final GenericObjectPoolConfig poolConfig, final String host, final int port, final int timeout) {
        super(new JSSDBFactory(host, port, timeout, null), poolConfig);
    }

    public JSSDBPool(final GenericObjectPoolConfig poolConfig, final String host, final int port, final int timeout, final String password) {
        super(new JSSDBFactory(host, port, timeout, password), poolConfig);
    }

    @Override
    public JSSDB getResource() {
        JSSDB jssdb = super.getResource();
        jssdb.setDataSource(this);
        return jssdb;
    }

    @Override
    public void returnBrokenResource(JSSDB resource) {
        if ( resource != null ) {
            super.returnBrokenResourceObject(resource);
        }
    }

    @Override
    public void returnResource(JSSDB resource) {
        if ( resource != null ) {
            try {
                returnResourceObject(resource);
            } catch ( Exception e ) {
                returnBrokenResource(resource);
                throw new JSSDBException("Could not return the resource to the pool", e);
            }
        }
    }
}
