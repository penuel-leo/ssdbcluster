package com.yeahmobi.ssdb.client;

import com.yeahmobi.ssdb.client.util.HostAndPort;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 13:13
 * @desc
 */
public class JSSDBFactory implements PooledObjectFactory<JSSDB> {

    private final AtomicReference<HostAndPort> hostAndPortAtomicReference = new AtomicReference<>();

    private final int timeout;

    private final String password;

    public JSSDBFactory(final String host, final int port, final int timeout, final String password) {
        this.hostAndPortAtomicReference.set(new HostAndPort(host, port));
        this.timeout = timeout;
        this.password = password;
    }

    public void setHostAndPort(final HostAndPort hostAndPort) {
        this.hostAndPortAtomicReference.set(hostAndPort);
    }

    @Override
    public PooledObject<JSSDB> makeObject() throws Exception {
        final HostAndPort hostAndPort = this.hostAndPortAtomicReference.get();
        final JSSDB jssdb = new JSSDB(hostAndPort.getHost(), hostAndPort.getPort());
        if ( null != password ) {
            jssdb.auth(password);
            //TODO auth result
        }
        return new DefaultPooledObject<>(jssdb);
    }

    @Override
    public void destroyObject(PooledObject<JSSDB> pooledObject) throws Exception {
        final JSSDB jssdb = pooledObject.getObject();
        if ( jssdb.isConnected() ) {
            jssdb.close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<JSSDB> pooledObject) {
        try {
            final JSSDB jssdb = pooledObject.getObject();
            HostAndPort hostAndPort = this.hostAndPortAtomicReference.get();
            String connectionHost = jssdb.getConnection().getHost();
            int connectionPort = jssdb.getConnection().getPort();
            return hostAndPort.getHost().equals(connectionHost) && hostAndPort.getPort() == connectionPort && jssdb.isConnected();
        } catch ( Exception e ) {
            //TODO logger
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<JSSDB> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<JSSDB> pooledObject) throws Exception {

    }
}
