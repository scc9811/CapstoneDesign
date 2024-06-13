package CapstoneProject.BackEndServer.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MapperConfig {
    @Bean
    public Map<String, Integer> getDayIndex() {
        Map<String, Integer> dayIndexMapper = new HashMap<>();
        // 초기값 설정
        dayIndexMapper.put("MONDAY", 1);
        dayIndexMapper.put("TUESDAY", 2);
        dayIndexMapper.put("WEDNESDAY", 3);
        dayIndexMapper.put("THURSDAY", 4);
        dayIndexMapper.put("FRIDAY", 5);
        dayIndexMapper.put("SATURDAY", 6);
        dayIndexMapper.put("SUNDAY", 7);
        return dayIndexMapper;
    }

}
