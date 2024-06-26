package CapstoneProject.BackEndServer.Handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class NetworkLatencyWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 현재 시간 구하기
        long serverTimeStamp = System.currentTimeMillis();


        // 클라이언트로부터 받은 JSON 메시지 파싱
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);

        // 클라이언트에서 보낸 clientTimeStamp 가져오기
        long clientTimeStamp = jsonNode.get("clientTimeStamp").asLong();

        // 클라이언트와 서버의 시간 차이 계산
        long latency = serverTimeStamp - clientTimeStamp;

        log.info("latency = " + latency);
        // 응답 메시지 생성
        JsonNode response = objectMapper.createObjectNode()
                .put("latency", latency);

        // 클라이언트에게 응답 메시지 전송
        session.sendMessage(new TextMessage(response.toString()));
    }
}
