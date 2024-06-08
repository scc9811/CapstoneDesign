package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.JwtData;
import CapstoneProject.BackEndServer.Dto.SignInRequest;
import CapstoneProject.BackEndServer.Dto.RequestResultData;
import CapstoneProject.BackEndServer.Dto.SignUpRequest;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import CapstoneProject.BackEndServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Slf4j
public class UserController {

    private final UserService userService;

    private final JsonFormatService<RequestResultData> jsonFormatService;

    private final JsonFormatService<JwtData> jwtDataJsonFormatService;

    @PostMapping("getJwt")
    public ResponseEntity<String> getJwt() {
        return ResponseEntity.ok().body(jwtDataJsonFormatService.formatToJson(new JwtData(userService.getJwt("test@gmail.com"))));
    }

    @GetMapping("getSecuredPage")
    public ResponseEntity<String> getSecuredPage() {
        return ResponseEntity.ok().body("permitted");
    }

    @PostMapping("secure")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        boolean signInResult = userService.getSignInResult(signInRequest);
        // 로그인 성공 시 jwt 발급하는 로직 추가해야됨.
        log.info("---sigInController ---" );
        log.info("loginInfo = " + signInRequest.toString());


        return ResponseEntity.ok().body(jsonFormatService.formatToJson(new RequestResultData(signInResult)));
    }

    @PostMapping("signUp")
    public ResponseEntity<String> singUp(@RequestBody SignUpRequest signUpRequest) {
        log.info("userInfo = " + signUpRequest.toString());
        boolean signUpResult = userService.getSignUpResult(signUpRequest);
        return ResponseEntity.ok().body(jsonFormatService.formatToJson(new RequestResultData(signUpResult)));
    }

}
