package com.bright.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By JianBin.Liu on 2019/5/5
 * Description:
 */
@Slf4j
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new StringDecoder());

        pipeline.addLast(new SimpleChannelInboundHandler<String>() {
            @Override
            protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                log.info(s);
            }
        });

    }
}
