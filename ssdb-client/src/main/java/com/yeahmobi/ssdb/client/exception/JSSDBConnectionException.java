package com.yeahmobi.ssdb.client.exception;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 13:21
 * @desc
 */
public class JSSDBConnectionException extends JSSDBException {

    private static final long serialVersionUID = -3959398175846740101L;

    public JSSDBConnectionException(String message) {
        super(message);
    }

    public JSSDBConnectionException(Throwable e) {
        super(e);
    }

    public JSSDBConnectionException(String message, Throwable e) {
        super(message, e);
    }
}
