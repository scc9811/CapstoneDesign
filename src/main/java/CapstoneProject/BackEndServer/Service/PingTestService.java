package CapstoneProject.BackEndServer.Service;


import CapstoneProject.BackEndServer.Dto.ClientAvgTimeData;
import CapstoneProject.BackEndServer.Entity.TestResult;
import CapstoneProject.BackEndServer.Entity.TestResultId;
import CapstoneProject.BackEndServer.Repository.TestResultRepository;
import CapstoneProject.BackEndServer.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PingTestService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final TestResultRepository testResultRepository;

    private final Map<String, Integer> dayIndexMapper;

    private final UserService userService;

    public boolean getIcmpPacketAllowed(String clientRemoteAddress) {
        boolean isAllowedICMP;
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + clientRemoteAddress);
            int returnVal = p1.waitFor();
            isAllowedICMP = (returnVal == 0);
        }
        catch (Exception e){
            isAllowedICMP = false;
        }
        return isAllowedICMP;
    }

    public boolean saveAndReturnStatus(String token, ClientAvgTimeData clientAvgTimeData) {
        String userEmail = JwtUtil.getUserEmail(token, secretKey);

        LocalDateTime now = LocalDateTime.now();
        TestResultId testResultId = new TestResultId();
        testResultId.setUserId(userService.getUserId(userEmail));
        testResultId.setDay(dayIndexMapper.get(now.getDayOfWeek().toString()));
        testResultId.setHour(now.getHour());
        log.info("testResultId = " + testResultId.toString());

        TestResult testResult = TestResult
                .builder()
                .id(testResultId)
                .averageTime(new BigDecimal(clientAvgTimeData.getAverageResponseTime()))
                .build();
        log.info("testResult = " + testResult.toString());

        try {
            testResultRepository.save(testResult);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<TestResult> getTestResults(String token) {
        String userEmail = JwtUtil.getUserEmail(token, secretKey);
//        long userId = userRepository.findByEmail(userEmail).get().getId();
        long userId = userService.getUserId(userEmail);
        log.info("userId : " + userId);
        return testResultRepository.findByIdUserId(userId);
    }

}