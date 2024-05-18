package CapstoneProject.BackEndServer.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
    private String email;
    private String password;

}
