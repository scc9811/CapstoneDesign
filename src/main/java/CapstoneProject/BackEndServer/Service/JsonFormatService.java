package CapstoneProject.BackEndServer.Service;

import CapstoneProject.BackEndServer.Objects.PingResponseTimeData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
//@Component
public class JsonFormatService<T>{
    private final ObjectMapper objectMapper;

//    public String formatToJson(PingResponseTimeData pingResponseTimeData) {
    public String formatToJson(T data) {
        try {
            // ObjectMapper를 사용하여 Java 객체를 JSON 문자열로 변환
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            // JSON 변환 중 오류가 발생한 경우 예외 처리
            e.printStackTrace();
            return null;
        }
    }
}
