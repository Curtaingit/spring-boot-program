package com.curtain.websocket.controller;

import javafx.scene.effect.SepiaTone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Curtain
 * @date 2019/10/12 9:27
 */
@Component
@ServerEndpoint("/webSocket/{page}")
public class WebSocket {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 总人数
     */
    private static AtomicInteger onlinePersons = new AtomicInteger(0);

    /**
     * 房间
     */
    private static Map<String, Set> roomMap = new ConcurrentHashMap<>(8);

    /**
     * 但收到客户端websocket请求时
     *
     * @param page    房间号
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void open(@PathParam("page") String page, Session session) throws IOException {
        Set set = roomMap.get(page);

        if (set == null) {
            set = new CopyOnWriteArraySet();
            set.add(session);
            roomMap.put(page, set);
        } else {
            set.add(session);
        }
        onlinePersons.incrementAndGet();
        log.info("新用户{}加入聊天，{}号房间人数{},总人数{}", session.getId(), page, set.size(), onlinePersons);
    }

    @OnClose
    public void close(@PathParam("page") String page, Session session) {
        if (roomMap.containsKey(page)) {
            roomMap.get(page).remove(session);
        }
        onlinePersons.decrementAndGet();
        log.info("用户{}退出聊天,{}号房间人数{},房间人数{}", session.getId(), page, roomMap.get(page).size(), onlinePersons);

    }

    @OnMessage
    public void reveiveMessage(@PathParam("page") String page, Session session, String message) throws IOException {
        log.info("接收到用户{}的数据：{}", session.getId(), message);
        String msg = session.getId() + ":" + message;
        Set<Session> sessions = roomMap.get(page);
        for (Session s : sessions) {
            s.getBasicRemote().sendText(msg);
        }
    }

    @OnError
    public void error(Throwable throwable) {
        try {
            throw throwable;
        } catch (Throwable e) {
            log.error("未知错误");
        }
    }
}
