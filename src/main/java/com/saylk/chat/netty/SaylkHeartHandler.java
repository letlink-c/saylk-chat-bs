package com.saylk.chat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

@Slf4j
public class SaylkHeartHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.READER_IDLE) { // 读空闲
               log.info("读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) { //写空闲
                log.info("写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("读写空闲");
               /* Channel channel = ctx.channel();
                channel.close();*/
            }
        }
    }

}
