package CapstoneProject.BackEndServer.Handler;

import CapstoneProject.BackEndServer.Dto.PingResponseTimeData;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import CapstoneProject.BackEndServer.Service.PingTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler{
//    private static final Map<String, WebSocketSession> CLIENTS = new HashMap<>();

    private static final long TIMEOUT = 30000;

    private final JsonFormatService<PingResponseTimeData> jsonFormatService_toPingResponseTimeData;

    private final PingTestService pingTestService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        CLIENTS.put(session.getId(), session);
        log.info("Socket Connection started");
        log.info("IP in WebSocketHandler : " + session.getRemoteAddress());
        String clientName = session.getRemoteAddress().getHostName();
        log.info("clientName = "+clientName);



        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed(session.getRemoteAddress().getHostName());

        // icmp denied : 방화벽 설정 안내 메세지, 세션 종료
        if(!isAllowedICMP) {
            log.info("ping test 불가. 방화벽 설정을 확인해주세요.");
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

                log.info("totalResponseTime = "+totalResponseTime);
                double averageResponseTime = totalResponseTime / responsePacketCount;


                // pingResponseTimeData setting.
                PingResponseTimeData pingResponseTimeData =
                        PingResponseTimeData.builder()
                        .running(true)
                        .averageResponseTime(String.format("%.2f", averageResponseTime))
                        .packetLossRate(null)
                        .build();
                // jason 파싱해서 전송.
                session.sendMessage(new TextMessage(jsonFormatService_toPingResponseTimeData.formatToJson(pingResponseTimeData)));
                log.info(line);
            }
            responsePacketCount--;

            double averageResponseTime = totalResponseTime / responsePacketCount;
            double packetLossRate = (int) ((double) lostPacketCount / (lostPacketCount + responsePacketCount) * 100);
            PingResponseTimeData pingResponseTimeData = PingResponseTimeData.builder()
                    .running(false)
                    .averageResponseTime(String.format("%.2f", averageResponseTime))
                    .packetLossRate(String.format("%.2f", packetLossRate) + "%")
                    .build();
            session.sendMessage(new TextMessage(jsonFormatService_toPingResponseTimeData.formatToJson(pingResponseTimeData)));


            // 프로세스 종료 및 자원 정리
            process.destroy();
            reader.close();
        } catch (IOException e) {
            log.info("Main Ping Test Process Error");
            e.printStackTrace();
        }

//         세션 종료
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Socket Connection ended");
    }
}
