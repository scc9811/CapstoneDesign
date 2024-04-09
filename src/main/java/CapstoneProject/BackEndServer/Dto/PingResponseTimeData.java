package CapstoneProject.BackEndServer.Dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PingResponseTimeData {
    private boolean running;

    private String averageResponseTime;

    private String packetLossRate;

}
