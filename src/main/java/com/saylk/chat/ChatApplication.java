package com.saylk.chat;

import com.saylk.chat.netty.SaylkServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class ChatApplication implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            SaylkServer.getInstance().start();
        }
    }
}
