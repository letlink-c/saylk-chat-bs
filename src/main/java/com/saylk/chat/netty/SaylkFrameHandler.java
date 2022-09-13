package com.saylk.chat.netty;

import com.saylk.chat.util.NettyUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import lombok.extern.slf4j.Slf4j;


/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

@Slf4j
public class SaylkFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        /*
        {
         id:"",
         code:"?",
         username:"?",
         receiverUser:"?",
         sendTime:"",
         msg:"?"
        }*/
        //解析json
        String name = ctx.channel().id().toString();
        log.info(msg);
        for (Channel channel : NettyUtil.channelGroup) {
            channel.writeAndFlush(new TextWebSocketFrame(name + ":" + msg));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
    //name + ":" + msg 作为消息存入消息队列
    //消息队列负责分发消息，分给数据库模块，缓存模块
    //更改业务，变成房间制，加入相同房间聊天，让netty支持wss
}


