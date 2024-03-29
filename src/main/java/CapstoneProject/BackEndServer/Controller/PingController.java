package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Service.PingTestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class PingController {
    private final PingTestService pingTestService;
    @PostMapping("/isICMPInboundAllowed")
    public boolean isICMPInboundAllowed(HttpServletRequest request){
        String userIP = request.getRemoteAddr();
        System.out.println("userIP = "+userIP);
        System.out.println(pingTestService.isHostReachable(userIP));
        return pingTestService.isHostReachable(userIP);
    }
}
