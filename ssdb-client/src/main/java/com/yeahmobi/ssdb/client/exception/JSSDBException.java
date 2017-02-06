package com.yeahmobi.ssdb.client.exception;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 13:20
 * @desc
 */
public class JSSDBException extends RuntimeException {

    private static final long serialVersionUID = 5453447767163236593L;

    public JSSDBException(String message) {
        super(message);
    }

    public JSSDBException(Throwable e) {
        super(e);
    }

    public JSSDBException(String message, Throwable e) {
        super(message, e);
    }
}
