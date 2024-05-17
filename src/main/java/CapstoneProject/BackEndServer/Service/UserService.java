package CapstoneProject.BackEndServer.Service;


import CapstoneProject.BackEndServer.Dto.User;
import CapstoneProject.BackEndServer.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String login(String userName, String password){
        // 가입된 유저 확인 로직 추가해야됨.
        return JwtUtil.createJwt(userName, secretKey, expiredMs);
    }

    public void signUp(User user) {
        log.info("pwd = " + user.getPassword());
        String encodedPwd = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("encodedPwd = " + encodedPwd);
        log.info(String.valueOf(bCryptPasswordEncoder.matches(user.getPassword(), encodedPwd)));




    }
}
