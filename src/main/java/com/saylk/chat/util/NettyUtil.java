package com.saylk.chat.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.ImmediateEventExecutor;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

public class NettyUtil {

    private static final AttributeKey<String> ATTR_KEY_USER_ID = AttributeKey.valueOf("userId");

    /**
     * 存储每个客户端接入进来时的 channel 对象
     * 主要用于使用 writeAndFlush 方法广播信息
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    /**
     * 用于客户端和服务端握手时存储用户id和netty Channel对应关系
     */
    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();


    public static void setUserId(Channel channel, String value) {
        channel.attr(ATTR_KEY_USER_ID).set(value);
    }

    public static String getUserId(Channel channel) {
        return getAttribute(channel, ATTR_KEY_USER_ID);
    }

    private static String getAttribute(Channel channel, AttributeKey<String> key) {
        Attribute<String> attr = channel.attr(key);
        return attr.get();
    }

    public static void clearSession(Channel channel) {
        // 清除会话信息
        channelGroup.remove(channel);
        channelMap.remove(channel.id().toString());
    }

    public static void addSession(Channel channel) {
        channelGroup.add(channel);
        channelMap.put(channel.id().toString(), channel);
    }

    public static SSLContext createSSLContext(String type , String path , String password) throws Exception {
        KeyStore ks = KeyStore.getInstance(type); /// "JKS"
        InputStream ksInputStream = Files.newInputStream(Paths.get(path)); /// 证书存放地址
        ks.load(ksInputStream, password.toCharArray());
        //KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());//getDefaultAlgorithm:获取默认的 KeyManagerFactory 算法名称。
        kmf.init(ks, password.toCharArray());
        //SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }

}
