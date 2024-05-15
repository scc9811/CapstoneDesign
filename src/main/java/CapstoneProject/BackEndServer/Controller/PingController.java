package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.ICMPInboundAccessData;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import CapstoneProject.BackEndServer.Service.PingTestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Slf4j
public class PingController {

    private final JsonFormatService<ICMPInboundAccessData> jsonFormatService_toICMPInboundAccessData;

    private final PingTestService pingTestService;

    @PostMapping("/isICMPInboundAllowed")
    public String isICMPInboundAllowed(HttpServletRequest request){
//        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed(request.getRemoteAddr());
        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed("127.0.0.1");
        ICMPInboundAccessData data = new ICMPInboundAccessData();
        data.setAllowed(isAllowedICMP);
        return jsonFormatService_toICMPInboundAccessData.formatToJson(data);
    }

    @PostMapping("/getClientIP")
    public String getClientIP(HttpServletRequest request){
        log.info("request getClientIP");
        return request.getRemoteAddr();
    }

}
