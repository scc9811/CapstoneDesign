package CapstoneProject.BackEndServer.Dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PingResponseTimeData {
    private boolean running;

    private String averageResponseTime;

    private String packetLossRate;

}
