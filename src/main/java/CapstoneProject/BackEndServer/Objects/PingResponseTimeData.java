package CapstoneProject.BackEndServer.Objects;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PingResponseTimeData {
    private boolean running;

    private String averageResponseTime;

    private String packetLossRate;

}
