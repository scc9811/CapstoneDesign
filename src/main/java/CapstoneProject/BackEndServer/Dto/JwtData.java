package CapstoneProject.BackEndServer.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtData {
    private String jwt;
    public JwtData(String jwt) {
        this.jwt = jwt;
    }
}
