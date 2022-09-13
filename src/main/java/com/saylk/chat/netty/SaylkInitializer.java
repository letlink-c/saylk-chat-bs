package com.saylk.chat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

public class SaylkInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 添加顺序是有说法的
     * ssl handler
     * http编解码器
     * 大数据流支持
     * 数据聚合
     * 心跳检测，ws自动处理handler
     * 自定义handler，先http，再websocket
     * @param socketChannel
     * @throws Exception
     */


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline
                .addLast(new HttpServerCodec())
                //对大数据流支持
                .addLast(new ChunkedWriteHandler())
                //数据聚合
                .addLast(new HttpObjectAggregator(1024 * 64))
                //心跳检测
                .addLast(new IdleStateHandler(10,20,30))
                .addLast("heartHandler",new SaylkHeartHandler())
                //自动处理非TextWebSocket的帧
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                //自定义handler
                .addLast(new SaylkRequestHandler())
                .addLast(new SaylkFrameHandler());
    }

}
