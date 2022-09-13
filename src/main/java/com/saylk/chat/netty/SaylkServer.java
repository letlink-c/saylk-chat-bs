package com.saylk.chat.netty;

import com.saylk.chat.constant.WebSocketConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

@Component
@Slf4j
public class SaylkServer {

    private static class SingletonIMServer {
        static final SaylkServer INSTANCE = new SaylkServer();
    }

    public static SaylkServer getInstance() {
        return SingletonIMServer.INSTANCE;
    }

    private final ServerBootstrap server;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;

    private SaylkServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SaylkInitializer());
    }

    public void start() {
        try {
            final Channel channel = server.bind(WebSocketConstant.WEB_SOCKET_PORT).sync().channel();
            channel.closeFuture().sync();
            log.debug("启动成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.debug("关闭");
        }
    }

}
