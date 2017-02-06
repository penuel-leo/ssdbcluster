package com.yeahmobi.ssdb.client.command;

import com.yeahmobi.ssdb.client.protocol.Response;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2017/2/5 16:21
 * @desc
 */
public interface BaseCommand {

    //################server##################
    Response.Status auth(String password);

}
