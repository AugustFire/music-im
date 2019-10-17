package com.music.music.im.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * create_time 2019/10/17
 *
 * @author yzx
 */
@Slf4j
public class HelloNettyClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        //解析客户端请求,遇到\n \r\n则认为是完整报文 然后
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        //将字节流转换成一个字符串
                        ch.pipeline().addLast(new StringDecoder());
                        //处理业务逻辑
                        ch.pipeline().addLast(new TimeClientHandler());
                    }
                });
        ChannelFuture f = null;
        try {
            f = b.connect(host, port).sync();
            log.info("TimeClient start on [{}]",port);
            f.channel().closeFuture().sync();
            log.info("debug002");
        } catch (InterruptedException e) {
            log.error("客户端错误[{}]", e.getMessage());
        } finally {
            workGroup.shutdownGracefully();
        }
    }

}
