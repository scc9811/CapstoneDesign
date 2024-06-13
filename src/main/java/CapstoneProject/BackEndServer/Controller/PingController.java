package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.ClientAvgTimeData;
import CapstoneProject.BackEndServer.Dto.ICMPInboundAccessData;
import CapstoneProject.BackEndServer.Dto.PingResponseTimeData;
import CapstoneProject.BackEndServer.Entity.TestResult;
import CapstoneProject.BackEndServer.Entity.TestResultId;
import CapstoneProject.BackEndServer.Repository.TestResultRepository;
import CapstoneProject.BackEndServer.Repository.UserRepository;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import CapstoneProject.BackEndServer.Service.PingTestService;
import CapstoneProject.BackEndServer.Service.UserService;
import CapstoneProject.BackEndServer.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Slf4j
public class PingController {

    @Value("${jwt.secret}")
    private String secretKey;

    private final JsonFormatService<ICMPInboundAccessData> jsonFormatService_toICMPInboundAccessData;

    private final PingTestService pingTestService;

    private final TestResultRepository testResultRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final Map<String, Integer> dayIndexMapper;

    @GetMapping("/isICMPInboundAllowed")
    public String isICMPInboundAllowed(HttpServletRequest request){
//        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed(request.getRemoteAddr());
        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed("127.0.0.1");
        ICMPInboundAccessData data = new ICMPInboundAccessData();
        data.setAllowed(isAllowedICMP);
        log.info("isAllowed = " + isAllowedICMP);
        return jsonFormatService_toICMPInboundAccessData.formatToJson(data);
    }

    @GetMapping("/getClientIP")
    public String getClientIP(HttpServletRequest request){
        System.out.println("ServletRequest. " + request.getRemoteAddr());
        log.info("request getClientIP");
        return request.getRemoteAddr();
    }

//    @GetMapping("/ping")
//    public void test(HttpServletRequest request) {
//        log.info("user ip : " + request.getRemoteAddr());
//        log.info("user port : " + request.getRemotePort());
//    }

    @PostMapping("/storeResult")
    public ResponseEntity<String> storeResult(HttpServletRequest request, @RequestBody ClientAvgTimeData clientAvgTimeData) {
        log.info("client's avg time : " + clientAvgTimeData.getAverageResponseTime());

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
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

        testResultRepository.save(testResult);

        return ResponseEntity.ok().body("");
    }

    @PostMapping("/getTestResult")
    public ResponseEntity<String> getTestResult() {
        TestResultId testResultId = new TestResultId();
        testResultId.setUserId(5L);
        testResultId.setDay(1);
        testResultId.setHour(5);
        Optional<TestResult> optionalTestResult = testResultRepository.findById(testResultId);
        log.info(String.valueOf(testResultRepository.findAll().size()));



        if(optionalTestResult.isEmpty()) log.info("empty");
        else log.info(optionalTestResult.get().toString());
        return ResponseEntity.ok().body("");
    }
}
