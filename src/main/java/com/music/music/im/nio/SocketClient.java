package com.music.music.im.nio;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * create_time 2019/10/14
 *
 * @author yzx
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        //开启客户端通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
        //发送请求
        ByteBuffer buffer = ByteBuffer.wrap("hello yzx!".getBytes());
        socketChannel.write(buffer);

        while (true) {
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int num;
            if ((num = socketChannel.read(readBuffer)) > 0) {
                readBuffer.flip();
                byte[] re = new byte[num];
                readBuffer.get(re);
                String result = new String(re, StandardCharsets.UTF_8);
                System.out.println("返回值:" + result);
            }
        }

    }
}
