package CapstoneProject.BackEndServer.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInRequest {

    private String email;

    private String passWord;

}
