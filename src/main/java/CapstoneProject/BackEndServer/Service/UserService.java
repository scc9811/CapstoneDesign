package CapstoneProject.BackEndServer.Service;


import CapstoneProject.BackEndServer.Dto.SignUpRequest;
import CapstoneProject.BackEndServer.Entity.User;
import CapstoneProject.BackEndServer.Repository.UserRepository;
import CapstoneProject.BackEndServer.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public String login(String userName, String password){
        // 가입된 유저 확인 로직 추가해야됨.
        return JwtUtil.createJwt(userName, secretKey, expiredMs);
    }

    public void signUp(SignUpRequest signUpRequest) {
        // test중...
        log.info("pwd = " + signUpRequest.getPassword());
        String encodedPwd = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
        log.info("encodedPwd = " + encodedPwd);
        log.info("match = " + String.valueOf(bCryptPasswordEncoder.matches(signUpRequest.getPassword(), encodedPwd)));

        List<User> userList = userRepository.findAll();
        for(User user : userList) {
            log.info("userEmail = " + user.getEmail());
        }


    }
}
