package com.yeahmobi.ssdb.client.protocol;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yeahmobi.ssdb.client.Tuple;

import java.util.*;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/1/9 下午3:29
 * @desc
 */
public class Response {

    private Status status = Status.NO_RESPONSE;

    private List<byte[]> content;

    public Response(List<byte[]> bytes) {
        if ( null == bytes || bytes.size() == 0 ) {
            this.status = Status.NO_RESPONSE;
        } else {
            if ( bytes.size() > 0 ) {
                this.status = Status.getByCode(new String(bytes.get(0)));
            }
            if ( bytes.size() > 1 ) {
                this.content = bytes.subList(1, bytes.size());
            }
        }
        this.status.setMessage(getErrorInfo());
    }

    public Status getStatus() {
        return status;
    }

    public List<byte[]> getContent() {
        return content;
    }

    public String getStringContent() {
        if ( Status.OK.equals(status) ) {
            return getContentString();
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

    public Set<String> getSetContent() {
        if ( Status.OK.equals(status) ) {
            Set<String> set = new LinkedHashSet<>();
            if ( content != null && content.size() > 0 ) {
                for ( byte[] bs : content ) {
                    set.add(new String(bs));
                }
            }
            return set;
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

    public Map<String, String> getMapContent() {
        if ( Status.OK.equals(status) ) {
            Map<String, String> map = Maps.newHashMap();
            if ( hasContent() ) {
                Iterator<byte[]> it = content.iterator();
                while ( it.hasNext() ) {
                    map.put(new String(it.next()), new String(it.next()));
                }
            }
            return map;
        }
        return null;
    }

    private boolean hasContent() {
        return null != content && content.size() > 0;
    }

    /**
     * 将content解析为字符串
     *
     * @return
     */
    private String getContentString() {
        if ( hasContent() ) {
            if ( content.size() == 1 ) {
                return new String(content.get(0));
            } else {
                StringBuilder sb = new StringBuilder();
                for ( int i = 0; i < content.size() - 1; i++ ) {
                    sb.append(new String(content.get(i))).append('\n');
                }
                sb.append(new String(content.get(content.size() - 1)));
                return sb.toString();
            }
        }
        return null;
    }

    private String getErrorInfo() {
        if ( !Status.OK.equals(status) ) {
            return getContentString();
        }
        return null;
    }

}
