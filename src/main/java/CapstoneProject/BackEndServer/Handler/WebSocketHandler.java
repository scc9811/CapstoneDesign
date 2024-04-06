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
import java.util.*;

// Compoent로 bean 등록하는 이유 ..?
@Component
public class WebSocketHandler extends TextWebSocketHandler{
    private static final Map<String, WebSocketSession> CLIENTS = new HashMap<>();

    private static final long TIMEOUT = 1000;
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


            // ping 결과 출력
            String line;
            int responseCount = 0;
            double totalResponseTime = 0.0;
            long startTime = System.currentTimeMillis();
            while ((line = reader.readLine()) != null &&
                    (System.currentTimeMillis() - startTime) < TIMEOUT
                    && responseCount++ < 20) {
                totalResponseTime += Double.parseDouble(line.split(" ")[6].split("=")[1]);
                System.out.println("totalResponseTime = "+totalResponseTime);
                double averageResponseTime = totalResponseTime / responseCount;
                session.sendMessage(new TextMessage(String.valueOf(averageResponseTime)));
                System.out.println(line);

                startTime = System.currentTimeMillis();
            }

            // ping test denied
            if(responseCount == 0){
                System.out.println("ping test 불가. 방화벽 설정을 확인해주세요.");
            }

            // 프로세스 종료 및 자원 정리
            process.destroy();
            reader.close();
        } catch (IOException e) {
            System.out.println("catch section");
            e.printStackTrace();
        }




//         세션 종료
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
        System.out.println("Socket Connection ended");
    }
}
