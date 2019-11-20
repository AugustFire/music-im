package com.music.music.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yzx
 * @date 2019/8/18
 */
@Slf4j
public class HelloNettyServer {

    public static void main(String[] args) {
        final int port = 8080;
        //接受客户端连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理客户端连接
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        //服务启动类
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup)
                //指定channel为NioServerSocketChannel对应于nio.ServerSocketChannel
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, 128)
                .attr(AttributeKey.valueOf("ssc.key"), "ssc.value")
                //初始化客户端连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        //解析客户端请求,遇到\n \r\n则认为是完整报文 然后
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        //将字节流转换成一个字符串
                        ch.pipeline().addLast(new StringDecoder());
                        //处理业务逻辑
                        ch.pipeline().addLast(new TimeServerHandler());
                    }
                });

        try {
            //开启监听端口,介接受客户端请求
            ChannelFuture f = b.bind(port).sync();
            log.info("TimeServer start on [{}]", port);
            f.channel().closeFuture().sync();
            log.info("debug001");

        } catch (InterruptedException e) {
            log.info("TimeServer error [{}]", e.getMessage());
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
