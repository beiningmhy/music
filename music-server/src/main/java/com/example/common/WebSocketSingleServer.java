package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.entity.Imsingle;
import com.example.service.ImsingleService;
import com.example.service.impl.ImsingleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * websocket服务-单聊
 */
@ServerEndpoint(value = "/imserverSingle")
@Component
public class WebSocketSingleServer implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(WebSocketSingleServer.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Resource
    ImsingleService imSingleService;

    static ImsingleService staticImSingleService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        sessionMap.put(session.getId(), session);
        log.info("[onOpen] 新建连接，session={}，当前在线人数：{}", session.getId(), sessionMap.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        sessionMap.remove(session.getId());
        log.info("[onClose] 有一连接关闭，session={}，当前在线人数：{}", session.getId(), sessionMap.size());
    }

    /**
     *  收到客户端消息后调用的方法
     *  后台收到客户端发送过来的消息
     *  onMessage 是一个消息的中转站
     *  接收 浏览器端 socket.send 发送过来的json数据
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session fromSession) {
        log.info("服务端收到消息：{}", message);
        Imsingle imsingle = JSONUtil.toBean(message, Imsingle.class);
        imsingle.setTime(DateUtil.now());
        //存储到数据库
        staticImSingleService.add(imsingle);
        String jsonStr = JSONUtil.toJsonStr(imsingle);
        this.sendAllMessage(jsonStr);
        log.info("[onMessage] 发送消息：{}", jsonStr);


    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("[onError] 发生错误", throwable);
    }

    /**
     * 服务端发送消息给所有客户端
     */
    public static void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("[sendAllMessage] 服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getAsyncRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("[sendAllMessage] 服务端给客户端发送消息失败", e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        staticImSingleService = imSingleService;
    }
}
















