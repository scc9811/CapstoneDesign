package CapstoneProject.BackEndServer.Service;


import CapstoneProject.BackEndServer.Dto.SignInRequest;
import CapstoneProject.BackEndServer.Dto.SignUpRequest;
import CapstoneProject.BackEndServer.Entity.User;
import CapstoneProject.BackEndServer.Repository.UserRepository;
import CapstoneProject.BackEndServer.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final Long expiredMs = 1000 * 60 * 60L;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public boolean getSignInResult(SignInRequest signInRequest){
        // 가입된 유저 확인 로직 추가해야됨.
        Optional<User> optionalUser = userRepository.findByEmail(signInRequest.getEmail());
        if(optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        return bCryptPasswordEncoder.matches(signInRequest.getPassword(), user.getPassword());
    }

    public String getJwt(String email) {
        return JwtUtil.createJwt(email, secretKey, expiredMs);
    }

    public boolean getSignUpResult(SignUpRequest signUpRequest) {
        // 이미 가입되어 있는 이메일인 경우
        if(isUserRegistered(signUpRequest.getEmail())) {
            log.info("이미 가입되어있는 이메일");
            return false;
        }

        log.info("pwd = " + signUpRequest.getPassword());
        String encodedPwd = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
        log.info("match = " + String.valueOf(bCryptPasswordEncoder.matches(signUpRequest.getPassword(), encodedPwd)));
        User user = User.builder()
                        .nickname(signUpRequest.getNickName())
                        .email(signUpRequest.getEmail())
                        .password(encodedPwd)
                        .build();
        log.info("---- in userService ----");
        log.info("---- user Info ----");


        log.info(user.toString());
        userRepository.save(user);
        return true;
    }

    public boolean isUserRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String getUserNickName(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
//        if(optionalUser.isEmpty()) return null;
//        return  optionalUser.get().getNickname();
        return optionalUser.map(User::getNickname).orElse(null);
    }

    public Long getUserId(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if(optionalUser.isEmpty()) return -1L;
        return optionalUser.get().getId();
    }

}
