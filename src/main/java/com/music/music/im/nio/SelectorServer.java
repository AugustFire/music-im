package com.music.music.im.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * create_time 2019/10/14
 *
 * @author yzx
 */
@Slf4j
public class SelectorServer {

    public static void main(String[] args) throws IOException {
        log.info("start socket server...");
        //多路复用器
        Selector selector = Selector.open();
        //开启TCP通道
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(8080));
        server.configureBlocking(false);
        //注册事件
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                //有新的连接
                if (key.isAcceptable()) {
                    //
                    log.info("监听到_新的连接");
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    //注册到Selector,监听OP_READ事件,等待数据
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    log.info("监听到_新的数据");
                    //有数据可读
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int num = socketChannel.read(readBuffer);
                    if (num > 0) {
                        System.out.println("收到的数据:" + new String(readBuffer.array()).trim());
                        ByteBuffer buffer = ByteBuffer.wrap("OOPS...".getBytes());
                        socketChannel.write(buffer);
                    }
                    if (num == -1) {
                        socketChannel.close();
                    }
                }
                iterator.remove();
            }

        }

    }
}
