package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.LoginRequest;
import CapstoneProject.BackEndServer.Dto.SignUpRequest;
import CapstoneProject.BackEndServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(userService.login(loginRequest.getUserName(), loginRequest.getPassWord()));
    }
    @PostMapping("signUp")
    public ResponseEntity<String> singUp(@RequestBody SignUpRequest signUpRequest) {
        // 1. 이미 가입 --> 안내메세지
        userService.signUp(signUpRequest);

        return ResponseEntity.ok().body("");
    }

}
