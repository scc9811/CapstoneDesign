package CapstoneProject.BackEndServer.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SignUpRequest {
    private String nickName;
    private String email;
    private String password;

}
