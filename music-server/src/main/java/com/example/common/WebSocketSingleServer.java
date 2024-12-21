package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
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
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * websocket服务-单聊
 */
@ServerEndpoint(value = "/imserverSingle/{username}")
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
    public void onOpen(Session session,@PathParam("username") String username) {
        sessionMap.put(username, session);
        log.info("[onOpen] 新建连接，session={}，当前在线人数：{}", session.getId(), sessionMap.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session,@PathParam("username") String username) {
        sessionMap.remove(username);
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
    public void onMessage(String message, Session fromSession,@PathParam("username") String username) {
//        log.info("服务端收到消息：{}", message);
//        Imsingle imsingle = JSONUtil.toBean(message, Imsingle.class);
//        imsingle.setTime(DateUtil.now());
//        //存储到数据库
//        staticImSingleService.add(imsingle);
//        String jsonStr = JSONUtil.toJsonStr(imsingle);
//        sendAllMessage(jsonStr);
//        log.info("[onMessage] 发送消息：{}", jsonStr);
        log.info("服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String touser = obj.getStr("touser"); // to表示发送给哪个用户，比如 admin
        String content = obj.getStr("content"); // 发送的消息文本  hello
        Imsingle imsingle = JSONUtil.toBean(message, Imsingle.class);
        imsingle.setTime(DateUtil.now());


        // {"to": "admin", "text": "聊天文本"}
        Session toSession = sessionMap.get(touser); // 根据 to用户名来获取 session，再通过session发送消息文本

        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("fromuser", username);  // from 是 zhang
            jsonObject.set("content", content);  // text 同上面的text
            sendMessage(jsonObject.toString(), toSession);
            imsingle.setReaded("0");
            log.info("发送给用户username={}，消息：{}", touser, jsonObject.toString());
        } else {
            imsingle.setReaded("1");
            log.info("发送失败，未找到用户username={}的session", touser);
        }
        staticImSingleService.add(imsingle);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("[onError] 发生错误", throwable);
    }

    /**
     * 服务端发送消息给所有客户端
     */
    public static void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        staticImSingleService = imSingleService;
    }
}
















