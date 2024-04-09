package CapstoneProject.BackEndServer.Handler;

import CapstoneProject.BackEndServer.Dto.PingResponseTimeData;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler{
//    private static final Map<String, WebSocketSession> CLIENTS = new HashMap<>();

    private static final long TIMEOUT = 30000;

    private final JsonFormatService<PingResponseTimeData> jsonFormatService_toPingResponseTimeData;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        CLIENTS.put(session.getId(), session);
        System.out.println("Socket Connection started");
        System.out.println("IP in WebSocketHandler : " + session.getRemoteAddress());
        String clientName = session.getRemoteAddress().getHostName();
        System.out.println("clientName = "+clientName);

        System.currentTimeMillis();

        boolean isAllowedICMP;
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + clientName);
            int returnVal = p1.waitFor();
            isAllowedICMP = (returnVal == 0);
        }
        catch (Exception e){
            System.out.println("First Ping Test Error");
            isAllowedICMP = false;
        }

        // icmp denied : 방화벽 설정 안내 메세지, 세션 종료
        if(!isAllowedICMP) {
            System.out.println("ping test 불가. 방화벽 설정을 확인해주세요.");
            session.sendMessage(new TextMessage("ping test 불가. 방화벽 설정을 확인해주세요."));
            session.close();
            return;
        }


        try {
            // ping 명령 실행
            Process process = Runtime.getRuntime().exec("ping " + clientName);

            // ping 결과를 읽어오기 위한 BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


            // ping 결과 출력
            int responsePacketCount = -1;
            int lostPacketCount = 0;
            double totalResponseTime = 0.0;
            String line;

            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime  < TIMEOUT && (line = reader.readLine()) != null &&
                    responsePacketCount++ < 10) {
                if ( responsePacketCount == 0 ) continue;

                String[] responseTokens = line.split(" ");
                lostPacketCount = Integer.parseInt(responseTokens[4].split("=")[1]) - responsePacketCount;

                totalResponseTime += Double.parseDouble(responseTokens[6].split("=")[1]);

                System.out.println("totalResponseTime = "+totalResponseTime);
                double averageResponseTime = totalResponseTime / responsePacketCount;


                // pingResponseTimeData setting.
                PingResponseTimeData pingResponseTimeData = new PingResponseTimeData();
                pingResponseTimeData.setRunning(true);
                pingResponseTimeData.setAverageResponseTime(String.format("%.2f", averageResponseTime));
                pingResponseTimeData.setPacketLossRate(null);
                // jason 파싱해서 전송.
                session.sendMessage(new TextMessage(jsonFormatService_toPingResponseTimeData.formatToJson(pingResponseTimeData)));
                System.out.println(line);
            }
            responsePacketCount--;

            PingResponseTimeData pingResponseTimeData = new PingResponseTimeData();
            pingResponseTimeData.setRunning(false);
            double averageResponseTime = totalResponseTime / responsePacketCount;
            pingResponseTimeData.setAverageResponseTime(String.format("%.2f", averageResponseTime));
            double packetLossRate = (int) ((double) lostPacketCount / (lostPacketCount + responsePacketCount) * 100);
            pingResponseTimeData.setPacketLossRate(String.format("%.2f", packetLossRate) + "%");
            session.sendMessage(new TextMessage(jsonFormatService_toPingResponseTimeData.formatToJson(pingResponseTimeData)));




//            session.sendMessage(new TextMessage("Avg responseTime = " + ( totalResponseTime / responsePacketCount) + "\n" +
//                                "Packet Loss Rate = " + (int) ( (double) lostPacketCount / (lostPacketCount + responsePacketCount) * 100) + "%"));



            // 프로세스 종료 및 자원 정리
            process.destroy();
            reader.close();
        } catch (IOException e) {
            System.out.println("Main Ping Test Process Error");
            e.printStackTrace();
        }


//         세션 종료
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        CLIENTS.remove(session.getId());
        System.out.println("Socket Connection ended");
    }
}
