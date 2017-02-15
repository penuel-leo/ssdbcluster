package com.yeahmobi.ssdb.client.command;

import com.yeahmobi.ssdb.client.protocol.Status;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 16:21
 * @desc
 */
public interface BaseCommand {

    //################server##################
    Status auth(String password);

    Long dbsize();

    //此命令客户端不支持,并且可能破坏主从关系,请谨慎在server command执行
    //void flushdb();

    String info();

}
