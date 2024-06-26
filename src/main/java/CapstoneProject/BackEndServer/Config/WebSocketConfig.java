package CapstoneProject.BackEndServer.Config;

import CapstoneProject.BackEndServer.Handler.WebSocketHandler;
import CapstoneProject.BackEndServer.Handler.NetworkLatencyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    private final NetworkLatencyWebSocketHandler networkLatencyWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // setAllowedOrigins("*") --> 나중에 프론트엔드 서버 주소로 바꿔야됨.
        registry.addHandler(webSocketHandler, "/socketConnection").setAllowedOrigins("*");
        registry.addHandler(networkLatencyWebSocketHandler, "/networkLatencyWebSocketConnection").setAllowedOrigins("*");
    }
}
