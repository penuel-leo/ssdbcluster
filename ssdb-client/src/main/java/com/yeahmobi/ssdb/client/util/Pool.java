package com.yeahmobi.ssdb.client.util;

import com.yeahmobi.ssdb.client.exception.JSSDBConnectionException;
import com.yeahmobi.ssdb.client.exception.JSSDBException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 15:51
 * @desc
 */
public class Pool<T> implements Closeable {

    protected GenericObjectPool<T> innerPool;

    public Pool() {
    }

    public boolean isClosed() {
        return this.innerPool.isClosed();
    }

    public Pool(final PooledObjectFactory<T> factory, final GenericObjectPoolConfig poolConfig) {
        initPool(factory, poolConfig);
    }

    public void initPool(PooledObjectFactory<T> factory, final GenericObjectPoolConfig poolConfig) {
        if ( this.innerPool != null ) {
            try {
                closeInnerPool();
            } catch ( Exception e ) {

            }
        }
        this.innerPool = new GenericObjectPool<T>(factory, poolConfig);
    }

    public T getResource() {
        try {
            return innerPool.borrowObject();
        } catch ( Exception e ) {
            throw new JSSDBConnectionException("Could not get a resource from the pool", e);
        }
    }

    public void returnResourceObject(final T resource) {
        if ( resource == null ) {
            return;
        }
        try {
            innerPool.returnObject(resource);
        } catch ( Exception e ) {
            throw new JSSDBException("Could not return the resource to the pool", e);
        }
    }

    public void returnBrokenResource(final T resource) {
        if ( resource != null ) {
            returnBrokenResourceObject(resource);
        }
    }

    public void returnResource(final T resource) {
        if ( resource != null ) {
            returnResourceObject(resource);
        }
    }

    protected void returnBrokenResourceObject(final T resource) {
        try {
            innerPool.invalidateObject(resource);
        } catch ( Exception e ) {
            throw new JSSDBException("Could not return the resource to the pool", e);
        }
    }

    protected void closeInnerPool() {
        try {
            innerPool.close();
        } catch ( Exception e ) {
            throw new JSSDBException("Could not destroy the pool", e);
        }
    }

    public int getNumActive() {
        if ( this.innerPool == null || this.innerPool.isClosed() ) {
            return -1;
        }

        return this.innerPool.getNumActive();
    }

    public int getNumIdle() {
        if ( this.innerPool == null || this.innerPool.isClosed() ) {
            return -1;
        }

        return this.innerPool.getNumIdle();
    }

    public int getNumWaiters() {
        if ( this.innerPool == null || this.innerPool.isClosed() ) {
            return -1;
        }

        return this.innerPool.getNumWaiters();
    }

    @Override
    public void close() throws IOException {
        closeInnerPool();
    }


}
