package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.LoginRequest;
import CapstoneProject.BackEndServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(userService.login(loginRequest.getUserName(), ""));
    }

}
