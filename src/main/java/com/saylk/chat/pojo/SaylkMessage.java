package com.saylk.chat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Saylk
 * @Date 2022/9/8
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaylkMessage {
    /** 消息id */
    private String id;
    /** 消息发送类型 */
    private Integer code;
    /** 发送人用户名 */
    private String username;
    /** 接收人用户名 */
    private String receiverUser;
    /** 发送时间 */
    private Date sendTime;
    /** 消息内容 */
    private String msg;

}
