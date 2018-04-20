package com.ctf.log.server.netty;

import com.ctf.log.server.netty.proto.MessageDecoder;
import com.ctf.log.server.netty.proto.MessageEncoder;
import com.ctf.log.server.utils.LogWrapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MT UDP服务端
 */
@Component("udpServer")
public class UdpServer{

    private static final LogWrapper logger = LogWrapper.getLogger(UdpServer.class);


    private static final int PORT = 18083;

    @Autowired
    private UdpMsgHandler udpMsgHandler;
    private EventLoopGroup group;
    private Bootstrap bootstrap;

    @PostConstruct
    public void start() throws Exception {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new MessageDecoder());
                        pipeline.addLast(new MessageEncoder());
                        pipeline.addLast(udpMsgHandler);
                    }
                }).localAddress(new InetSocketAddress(PORT));
        try {
            Channel channel = bootstrap.bind().syncUninterruptibly().channel();
            logger.debug("Server running at port" + PORT);
            System.out.println("Server running");
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
