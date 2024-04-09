package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.ICMPInboundAccessData;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class PingController {
    private final JsonFormatService<ICMPInboundAccessData> jsonFormatService_toICMPInboundAccessData;
    @PostMapping("/isICMPInboundAllowed")
    public String isICMPInboundAllowed(HttpServletRequest request){
        boolean isAllowedICMP;
        try {
//            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + request.getRemoteAddr());
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + "127.0.0.1");
//            System.out.println("remoteaddr = " + request.getRemoteAddr());
            int returnVal = p1.waitFor();
            isAllowedICMP = (returnVal == 0);
            System.out.println("isAllowedICMP = " + isAllowedICMP);
        }
        catch (Exception e){
            System.out.println("First Ping Test Error");
            isAllowedICMP = false;
        }
        ICMPInboundAccessData data = new ICMPInboundAccessData();
        data.setAllowed(isAllowedICMP);
        return jsonFormatService_toICMPInboundAccessData.formatToJson(data);
    }
    @PostMapping("/getClientIP")
    public String getClientIP(HttpServletRequest request){
        System.out.println("request getClientIP");
        return request.getRemoteAddr();
    }
}
