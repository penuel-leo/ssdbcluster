package com.yeahmobi.ssdb.client.connection;

import com.google.common.collect.Lists;
import com.yeahmobi.ssdb.client.exception.JSSDBConnectionException;
import com.yeahmobi.ssdb.client.protocol.MemoryStream;
import com.yeahmobi.ssdb.client.protocol.Protocol;
import com.yeahmobi.ssdb.client.protocol.Protocol.Command;
import com.yeahmobi.ssdb.client.protocol.Response;
import com.yeahmobi.ssdb.client.protocol.SafeEncoder;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 13:16
 * @desc
 */
public class Connection implements Closeable {

    private String host = Protocol.DEFAULT_HOST;

    private int port = Protocol.DEFAULT_PORT;

    private int connectionTimeout = Protocol.DEFAULT_TIMEOUT;

    private int soTimeout = Protocol.DEFAULT_TIMEOUT;

    private int bufferSize = Protocol.BUFFER_SIZE;

    private boolean broken = false;

    private Socket socket;

    private MemoryStream memoryStream = new MemoryStream();

    public Connection() {
    }

    public Connection(final String host) {
        this.host = host;
    }

    public Connection(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public Connection(final String host, final int port, final int connectionTimeout, final int soTimeout) {
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
    }

    public Connection(final String host, final int port, final int connectionTimeout, final int soTimeout, final int bufferSize) {
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.bufferSize = bufferSize;
    }

    public Connection sendCommand(final Command cmd, final String... args) {
        List<byte[]> list = Lists.newArrayList();
        for ( String arg : args ) {
            if ( null != arg ) {
                list.add(SafeEncoder.encode(arg));
            }
        }
        return sendCommand(cmd, list);
    }

    protected Connection sendCommand(final Command cmd, final List<byte[]> args) {
        try {
            connect();
            MemoryStream buffer = new MemoryStream(bufferSize);
            int len = cmd.name.length();
            buffer.write(String.valueOf(len));
            buffer.write('\n');
            buffer.write(cmd.name);
            buffer.write('\n');

            Iterator<byte[]> it = args.iterator();
            byte[] bs;
            while ( it.hasNext() ) {
                bs = it.next();
                len = bs.length;
                buffer.write(String.valueOf(len));
                buffer.write('\n');
                buffer.write(bs);
                buffer.write('\n');
            }
            buffer.write('\n');

            OutputStream os = socket.getOutputStream();
            os.write(buffer.toArray());
            os.flush();
            return this;
        } catch ( JSSDBConnectionException e ) {
            broken = true;
            throw e;
        } catch ( IOException e ) {
            broken = true;
            throw new JSSDBConnectionException(e);
        }
    }

    public Response getResponse() {
        return new Response(recvResponse());
    }

    protected List<byte[]> recvResponse() {
        try {
            memoryStream.nice();
            List<byte[]> result;
            InputStream is = socket.getInputStream();
            while ( true ) {
                result = parse();
                if ( result != null ) {
                    return result;
                }
                byte[] bs = new byte[bufferSize];
                int len = is.read(bs);
                memoryStream.write(bs, 0, len);
            }

        } catch ( JSSDBConnectionException e ) {
            broken = true;
            throw e;
        } catch ( IOException e ) {
            broken = true;
            throw new JSSDBConnectionException(e);
        }
    }

    public void connect() {
        if ( !isConnected() ) {
            try {
                socket = new Socket();
                socket.setReuseAddress(true);
                socket.setKeepAlive(true); // Will monitor the TCP connection is
                socket.setTcpNoDelay(true); // Socket buffer Whetherclosed, to
                socket.setSoLinger(true, 0); // Control calls close () method,
                socket.connect(new InetSocketAddress(host, port), connectionTimeout);
                socket.setSoTimeout(soTimeout);
            } catch ( IOException ex ) {
                broken = true;
                throw new JSSDBConnectionException(ex);
            }
        }
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

    public void disconnect() {
        if ( isConnected() ) {
            try {
                if ( !socket.isClosed() ) {
                    socket.close();
                }
            } catch ( IOException ex ) {
                broken = true;
                throw new JSSDBConnectionException(ex);
            }
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }

    private List<byte[]> parse() throws JSSDBConnectionException {
        List<byte[]> list = Lists.newArrayList();

        int idx = 0;
        // ignore leading empty lines
        while ( idx < memoryStream.size && (memoryStream.chatAt(idx) == '\r' || memoryStream.chatAt(idx) == '\n') ) {
            idx++;
        }

        while ( idx < memoryStream.size ) {
            int data_idx = memoryStream.memchr('\n', idx);
            if ( data_idx == -1 ) {
                break;
            }
            data_idx += 1;

            int head_len = data_idx - idx;
            if ( head_len == 1 || (head_len == 2 && memoryStream.chatAt(idx) == '\r') ) {
                memoryStream.decr(data_idx);
                return list;
            }
            String str = new String(memoryStream.copyOfRange(idx, data_idx));
            str = str.trim();
            int size;
            try {
                size = Integer.parseInt(str, 10);
            } catch ( Exception e ) {
                throw new JSSDBConnectionException("parse body len fail, str:" + str, e);
            }
            idx = data_idx + size;
            int left = memoryStream.size - idx;
            if ( left >= 1 && memoryStream.chatAt(idx) == '\n' ) {
                idx += 1;
            } else if ( left >= 2 && memoryStream.chatAt(idx) == '\r' && memoryStream.chatAt(idx + 1) == '\n' ) {
                idx += 2;
            } else if ( left >= 2 ) {
                throw new JSSDBConnectionException("bad format");
            } else {
                break;
            }
            byte[] data = memoryStream.copyOfRange(data_idx, data_idx + size);
            list.add(data);
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
