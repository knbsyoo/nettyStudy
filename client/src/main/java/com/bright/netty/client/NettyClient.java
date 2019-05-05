package com.bright.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created By JianBin.Liu on 2019/5/5
 * Description:
 */
@Slf4j
@Component
public class NettyClient {

    private static int port;


    @Value("${netty.port}")
    public void setPort(int port){
        log.info("port222:" + port);
        this.port = port;
    }

    public static void start() throws InterruptedException {
        log.info("port111:" + port);
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", port).channel();

        while (true) {
            log.info(new java.util.Date() + ": hello world!");
            channel.writeAndFlush(new java.util.Date() + ": hello world!");
            Thread.sleep(2000);
        }
    }
}
