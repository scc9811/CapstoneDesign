package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.ICMPInboundAccessData;
import CapstoneProject.BackEndServer.Service.JsonFormatService;
import CapstoneProject.BackEndServer.Service.PingTestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Slf4j
public class PingController {

    private final JsonFormatService<ICMPInboundAccessData> jsonFormatService_toICMPInboundAccessData;

    private final PingTestService pingTestService;

    @GetMapping("/isICMPInboundAllowed")
    public String isICMPInboundAllowed(HttpServletRequest request){
//        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed(request.getRemoteAddr());
        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed("127.0.0.1");
        ICMPInboundAccessData data = new ICMPInboundAccessData();
        data.setAllowed(isAllowedICMP);
        log.info("isAllowed = " + isAllowedICMP);
        return jsonFormatService_toICMPInboundAccessData.formatToJson(data);
    }

    @PostMapping("/getClientIP")
    public String getClientIP(HttpServletRequest request){
        System.out.println("ServletRequest. " + request.getRemoteAddr());
        log.info("request getClientIP");
        return request.getRemoteAddr();
    }

    @PostMapping("/storeResult")
    public ResponseEntity<String> storeResult(HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        log.info("h = " + now.getHour());
        log.info("d = " + now.getDayOfWeek());

        return ResponseEntity.ok().body("");
    }

}
