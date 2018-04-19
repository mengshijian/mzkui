package com.ctf.log.server.netty.proto;

import com.ctf.ass_public.utils.ConvUtils;
import com.ctf.log.server.utils.LogWrapper;
import com.ctf.log4droid.mt_body.Term2Server;
import com.ctf.oss.CCM;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/*
 BinaryMsg解码器(TLV)
 */
public class MessageDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private static LogWrapper logger = LogWrapper.getLogger(MessageDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) {
        ByteBuf buf = packet.content();
        int len = buf.writerIndex() - buf.readerIndex();
        byte[] body_bytes = new byte[len];
        try {
            buf.readBytes(body_bytes);
            Term2Server term2Server = Term2Server.parseFrom(body_bytes);
            CCM.putSender(ctx, packet.sender());
            logger.debug(packet.sender(), "MT=>Server " + term2Server.toString());
            out.add(term2Server);
        } catch (Exception e) {
            logger.error(packet.sender(), "MessageDecoder.decode=> [decode]:BODY ERROR! body_bytes:" + ConvUtils.bytesToHexStr(body_bytes));
        }

    }
}