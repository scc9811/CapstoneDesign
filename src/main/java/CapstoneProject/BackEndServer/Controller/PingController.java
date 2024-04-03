package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Service.PingTestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpRequest;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class PingController {
//    private final PingTestService pingTestService;
//    @PostMapping("/isICMPInboundAllowed")

//    public boolean isICMPInboundAllowed(HttpServletRequest request){
//        String userIP = request.getRemoteAddr();
//        System.out.println("userIP = "+userIP);
//        System.out.println(pingTestService.isHostReachable(userIP));
//        return pingTestService.isHostReachable(userIP);
//    }
    @PostMapping("/getClientIP")
    public String getClientIP(HttpServletRequest request){
        return request.getRemoteAddr();
    }
    @PostMapping("/test")
    public boolean test(HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        System.out.println("test");
        System.out.println("ipAddress = "+ipAddress);
        try {
            // ping 명령 실행
            Process process = Runtime.getRuntime().exec("ping " + ipAddress);

            // ping 결과를 읽어오기 위한 BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            // ping 결과 출력
            int count = 10;
            while ((line = reader.readLine()) != null && count-->0) {
                System.out.println(line);
            }


            // 프로세스 종료 및 자원 정리
            process.destroy();
            reader.close();
        } catch (IOException e) {
            System.out.println("catch section");
            e.printStackTrace();
        }
        return true;
    }
}
