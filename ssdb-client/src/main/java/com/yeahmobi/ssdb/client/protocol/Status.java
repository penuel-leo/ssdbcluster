package com.yeahmobi.ssdb.client.protocol;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/15 10:03
 * @desc
 */
public enum Status {

    OK("ok"), NOT_FOUND("not_found"), CLIENT_ERROR("client_error"), UNKNOWN("unknown"), NO_RESPONSE("no_response");

    private String code;

    private String message;

    Status(String code) {
        this.code = code;
    }

    static Status getByCode(String code) {
        switch ( code ) {
        case "ok":
            return OK;
        case "not_found":
            return NOT_FOUND;
        case "client_error":
            return CLIENT_ERROR;
        default:
            return UNKNOWN;
        }
    }

    void setMessage(String message) {
        this.message = message;
    }
}
