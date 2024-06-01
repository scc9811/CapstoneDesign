package CapstoneProject.BackEndServer.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestResultData {
    private boolean result;
    public RequestResultData(boolean result) {
        this.result = result;
    }
}
