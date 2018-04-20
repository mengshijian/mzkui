package com.ctf.logtest.netty;

import com.ctf.log4droid.mt_body.Server2Term;
import com.ctf.log4droid.mt_body.Term2Server;
import com.ctf.log4droid.mt_body.TermReq;
import com.ctf.logtest.LogWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UdpMsgHandlerClient extends SimpleChannelInboundHandler<Server2Term> {

    private static LogWrapper logger = LogWrapper.getLogger(UdpMsgHandlerClient.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Server2Term server2Term){

        logger.debug(ctx, "UdpMsgHandlerClient.channelRead0=> " + server2Term);
        if (null != server2Term) {
            //发送
            Term2Server term2Server = new Term2Server();
            term2Server.setException("error!!!!!!!!!!!!!!!!!");
            term2Server.setPkgUid(Common.getPkg_uid());
            TermReq termReq = new TermReq();
            termReq.setSessionId(222);
            term2Server.setTermRequest(termReq);
            ctx.writeAndFlush(term2Server);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送
        Term2Server term2Server = new Term2Server();
        term2Server.setException("Oh!!!!!!!!!!!!!!!!!");
        term2Server.setPkgUid(Common.getPkg_uid());
        TermReq termReq = new TermReq();
        termReq.setSessionId(111);
        term2Server.setTermRequest(termReq);
        ctx.writeAndFlush(term2Server);
    }
}