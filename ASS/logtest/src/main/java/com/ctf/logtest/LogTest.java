package com.ctf.logtest;

import com.ctf.logtest.netty.UdpMsgHandlerClient;
import com.ctf.logtest.netty.proto.MessageDecoder;
import com.ctf.logtest.netty.proto.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * MT UDP服务端
 */
public class LogTest{
    private static final LogWrapper logger = LogWrapper.getLogger(LogTest.class.getName());

    static final String IP = "172.30.20.99";
    static final int PORT = 18888;



    public static void main(String[] args) throws Exception {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(
                                    new MessageEncoder(IP,PORT),
                                    new MessageDecoder(),
                                    new UdpMsgHandlerClient());
                        }
                    });

            logger.debug("连接到服务器");
            // Start the client.
            ChannelFuture f = b.connect(IP, PORT).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        }
        finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }

    }

}
