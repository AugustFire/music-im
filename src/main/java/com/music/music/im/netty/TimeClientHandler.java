package com.music.music.im.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * create_time 2019/10/17
 *
 * @author yzx
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req = ("QUERY1 TIME ORDER" + System.getProperty("line.separator")).getBytes();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        log.info("Now is:{}", body);
    }
}
