package com.ctf.log.server.netty.proto;


import com.ctf.log.server.utils.LogWrapper;
import com.ctf.log4droid.mt_body.Server2Term;
import com.ctf.oss.CCM;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<Server2Term> {
    private static LogWrapper logger = LogWrapper.getLogger(MessageEncoder.class.getName());


    @Override
    protected void encode(ChannelHandlerContext ctx, Server2Term server2Term, List<Object> out) throws Exception {
        if (server2Term == null) {
            logger.error(server2Term.getSid(), "[encode ERROR] server2Term is NULL!");
            throw new Exception("[encode ERROR] server2Term is NULL!");
        }

        ByteBuf buf = ctx.alloc().buffer();
        server2Term.toBytes(buf);

        out.add(new DatagramPacket(buf, CCM.getCmdBySid(server2Term.getSid()).getSender()));

        //打印log
        logger.debug(server2Term.getSid(), "Server=>Mt " + server2Term);
    }


}
