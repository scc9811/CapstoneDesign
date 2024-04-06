package CapstoneProject.BackEndServer.Handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Compoent로 bean 등록하는 이유 ..?
@Component
public class WebSocketHandler extends TextWebSocketHandler{
    private static final Map<String, WebSocketSession> CLIENTS = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
        System.out.println("Socket Connection started");
        System.out.println("IP in WebSocketHandler : " + session.getRemoteAddress());
        String clientName = session.getRemoteAddress().getHostName();
        System.out.println("clientName = "+clientName);

        try {
            // ping 명령 실행
            Process process = Runtime.getRuntime().exec("ping " + clientName);

            // ping 결과를 읽어오기 위한 BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            // ping 결과 출력
            int count = 20;
            while ((line = reader.readLine()) != null && count-->0) {
                session.sendMessage(new TextMessage(line));
                System.out.println(line);
            }

            // 프로세스 종료 및 자원 정리
            process.destroy();
            reader.close();
        } catch (IOException e) {
            System.out.println("catch section");
            e.printStackTrace();
        }




        // 세션 종료
//        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
        System.out.println("Socket Connection ended");
    }
}
