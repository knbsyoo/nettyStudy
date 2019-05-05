package com.bright.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created By JianBin.Liu on 2019/5/5
 * Description:
 */
@Slf4j
@Component
public class NettyServer {

    /**
     * 单例模式
     */
    public static NettyServer nettyServer = null;
    /**
     * netty lister port
     */
    private static int port;
    /**
     * 主线程池，维护连接
     */
    private EventLoopGroup boos = null;
    /**
     * 从线程池，I/O处理
     */
    private EventLoopGroup work = null;
    /**
     * 初始化ServerBootstrap实例， 此实例是netty服务端应用开发的入口
     */
    private ServerBootstrap bootstrap = null;

    private NettyServer() {

    }

    private NettyServer(int port) {
        this.port = port;

    }

    /**
     * 单例模式
     *
     * @return
     */
    public static NettyServer getInstance() {
        if (nettyServer == null) {
            synchronized (NettyServer.class) {
                if (nettyServer == null) {
                    nettyServer = new NettyServer(port);
                }
            }
        }
        return nettyServer;
    }

    @Value("${netty.port}")
    public void setPort(int port) {
        this.port = port;
    }


    public void init() throws InterruptedException {
        try {
            bootstrap = new ServerBootstrap();

            boos = new NioEventLoopGroup();
            work = new NioEventLoopGroup();

            bootstrap.group(boos, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyChannelInitializer());

            log.info("port:" + port);
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                log.info("netty server start...");
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

}
