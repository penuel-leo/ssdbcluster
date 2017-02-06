package com.yeahmobi.ssdb.client.exception;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 13:40
 * @desc
 */
public class JSSDBDataException extends JSSDBException {

    private static final long serialVersionUID = 2755436911510179324L;

    public JSSDBDataException(String message) {
        super(message);
    }

    public JSSDBDataException(Throwable e) {
        super(e);
    }

    public JSSDBDataException(String message, Throwable e) {
        super(message, e);
    }
}
