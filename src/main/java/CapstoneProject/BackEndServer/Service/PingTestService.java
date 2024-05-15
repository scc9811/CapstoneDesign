package CapstoneProject.BackEndServer.Service;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
@NoArgsConstructor
public class PingTestService {
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
}