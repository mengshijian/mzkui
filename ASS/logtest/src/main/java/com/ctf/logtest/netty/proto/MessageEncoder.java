package com.ctf.logtest.netty.proto;

import com.ctf.log4droid.mt_body.Term2Server;
import com.ctf.logtest.LogWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<Term2Server> {
    private static LogWrapper logger = LogWrapper.getLogger(MessageEncoder.class.getName());
    private final InetSocketAddress remoteAddress;

    public MessageEncoder(String ip, int port) {
        this.remoteAddress = new InetSocketAddress(ip, port);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Term2Server term2Server, List<Object> out) throws Exception {
        if (term2Server == null) {
            logger.error(ctx, "[encode ERROR] term2Server is NULL!");
            throw new Exception("[encode ERROR] term2Server is NULL!");
        }
        logger.debug(ctx, "Mt=>Server " + term2Server);


        ByteBuf buf = ctx.alloc().buffer();
        //编码
        int lenWriterIndex = buf.writerIndex();
        //写入body
        int len = term2Server.toBytes(buf);
        //回写len
        int crcWriterIndex = buf.writerIndex();
        buf.writerIndex(lenWriterIndex);
        buf.writeShort(len);
        buf.writerIndex(crcWriterIndex);

        out.add(new DatagramPacket(buf, remoteAddress));


    }

}
