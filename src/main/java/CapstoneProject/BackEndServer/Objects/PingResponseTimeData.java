package CapstoneProject.BackEndServer.Objects;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PingResponseTimeData {
    private String isRunning;

    private String averageResponseTime;

    private String packetLossRate;

}
