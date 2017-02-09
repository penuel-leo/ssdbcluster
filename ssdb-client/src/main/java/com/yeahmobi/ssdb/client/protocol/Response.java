package com.yeahmobi.ssdb.client.protocol;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yeahmobi.ssdb.client.Tuple;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/1/9 下午3:29
 * @desc
 */
public class Response {

    private Status status = Status.UNKNOWN;

    private List<byte[]> content;

    private String errorInfo;

    public Response(List<byte[]> bytes) {
        if ( null == bytes || bytes.size() == 0 ) {
            this.status = Status.UNKNOWN;
        } else {
            if ( bytes.size() > 0 ) {
                this.status = Status.getByCode(new String(bytes.get(0)));
            }
            if ( Status.CLIENT_ERROR.equals(status) && bytes.size() == 2 ) {
                this.errorInfo = new String(bytes.get(1));
                //TODO warn logit
            }
            if ( bytes.size() > 1 ) {
                this.content = bytes.subList(1, bytes.size());
            }
        }
    }

    public Status getStatus() {
        return status;
    }

    public List<byte[]> getContent() {
        return content;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public String getStringContent() {
        if ( Status.OK.equals(status) && hasContent() ) {
            if ( content.size() == 1 ) {
                return new String(content.get(0));
            } else {
                StringBuilder sb = new StringBuilder();
                for ( int i = 0; i < content.size() - 1; i++ ) {
                    sb.append(content.get(i)).append('\n');
                }
                sb.append(content.get(content.size() - 1));
                return sb.toString();
            }
        }
        return null;
    }

    public Long getLongContent() {
        if ( Status.OK.equals(status) ) {
            return Long.parseLong(new String(content.get(0)));
        }
        return null;
    }

    public Integer getIntegerContent() {
        if ( Status.OK.equals(status) ) {
            return Integer.parseInt(new String(content.get(0)));
        }
        return null;
    }

    public List<String> getListContent() {
        if ( Status.OK.equals(status) ) {
            List<String> list = Lists.newArrayList();
            if ( content != null && content.size() > 0 ) {
                for ( byte[] bs : content ) {
                    list.add(new String(bs));
                }
            }
            return list;
        }
        return null;
    }

    public Set<Tuple> getTupleContent() {
        if ( Status.OK.equals(status) ) {
            LinkedHashSet<Tuple> set = Sets.newLinkedHashSet();
            if ( hasContent() ) {
                Iterator<byte[]> it = content.iterator();
                while ( it.hasNext() ) {
                    set.add(new Tuple(new String(it.next()), Long.parseLong(new String(it.next()))));
                }
            }
            return set;
        }
        return null;
    }

    private boolean hasContent() {
        return null != content && content.size() > 0;
    }

    public enum Status {

        OK("ok"), NOT_FOUND("not_found"), CLIENT_ERROR("client_error"), UNKNOWN("unknown");

        private String code;

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
    }
}
