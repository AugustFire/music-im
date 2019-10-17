package com.music.music.im.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * 客户端请求处理器
 * create_time 2019/10/15
 *
 * @author yzx
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String request = (String) msg;
        String response = null;
        if ("QUERY TIME ORDER".equals(request)) {
            response = LocalDateTime.now().toString();
        }else {
            response = "404 Bad";
        }
        response = response + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
        ctx.writeAndFlush(resp);
    }
}
