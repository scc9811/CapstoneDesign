package CapstoneProject.BackEndServer.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class SignInRequest {

    private String email;

    private String password;

}
