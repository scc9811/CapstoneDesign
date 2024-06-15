package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.JwtData;
import CapstoneProject.BackEndServer.Dto.SignInRequest;
import CapstoneProject.BackEndServer.Dto.RequestResultData;
import CapstoneProject.BackEndServer.Dto.SignUpRequest;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import CapstoneProject.BackEndServer.Service.UserService;
import CapstoneProject.BackEndServer.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("getJwt")
    public ResponseEntity<String> getJwt() {

        return ResponseEntity.ok().body(jwtDataJsonFormatService.formatToJson(new JwtData(userService.getJwt("user1@gmail.com"))));
    }

    @GetMapping("getSecuredPage")
    public ResponseEntity<String> getSecuredPage() {
        return ResponseEntity.ok().body("permitted");
    }

    @GetMapping("requestMyPageData")
    public ResponseEntity<String> getMyPageData() {
        // test ~~
        return ResponseEntity.ok().body(jsonFormatService.formatToJson(new RequestResultData(true)));
    }

    @PostMapping("signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        boolean signInResult = userService.getSignInResult(signInRequest);
        if(!signInResult) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rejected");
        }
        else {
            log.info("loginInfo = " + signInRequest.toString());
            return ResponseEntity
                    .ok()
                    .body(jwtDataJsonFormatService.formatToJson(new JwtData(userService.getJwt(signInRequest.getEmail()))));
        }
    }

    @PostMapping("signUp")
    public ResponseEntity<String> singUp(@RequestBody SignUpRequest signUpRequest) {
        log.info("userInfo = " + signUpRequest.toString());
        boolean signUpResult = userService.getSignUpResult(signUpRequest);
        log.info("signUpResult = " + signUpResult);
        if(!signUpResult) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입되어있는 이메일입니다.");
        }
        else {
            return ResponseEntity.ok().body(jsonFormatService.formatToJson(new RequestResultData(signUpResult)));
        }
    }

    @PostMapping("isLoggedIn")
    public ResponseEntity<String> isLoggedIn(HttpServletRequest request) {
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
//        if(JwtUtil.isExpired(token, secretKey)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("isExpired");
//        }
        return ResponseEntity.ok().body("isAuthenticated");
    }

    @PostMapping("getUserNickName")
    public ResponseEntity<String> getUserNickName(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String userEmail = JwtUtil.getUserEmail(token, secretKey);
        String nickName = userService.getUserNickName(userEmail);
        if(nickName == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        return ResponseEntity.ok().body(nickName);
    }

}
