package com.ctf.logtest.netty.proto;

import com.ctf.ass_public.utils.ConvUtils;
import com.ctf.log4droid.mt_body.Server2Term;
import com.ctf.logtest.LogWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/*
 BinaryMsg解码器(TLV)
 */

public class MessageDecoder extends MessageToMessageDecoder<DatagramPacket> {

    private static LogWrapper logger = LogWrapper.getLogger(MessageDecoder.class.getName());

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out){
        logger.debug(ctx,"decode");

        ByteBuf buf = msg.content();

        int len = buf.readShort()  & 0xFFFF;
        byte[] body_bytes = new byte[len];
        buf.readBytes(body_bytes);
        try {
            Server2Term server2Term = Server2Term.parseFrom(body_bytes);
            logger.debug(ctx,"Server=>MT " + server2Term.toString());
            out.add(server2Term);
        }catch (Exception e){
            logger.error(ctx,"MessageDecoder.decode=> [decode]:BODY ERROR! body_bytes:" + ConvUtils.bytesToHexStr(body_bytes));
        }
    }
}