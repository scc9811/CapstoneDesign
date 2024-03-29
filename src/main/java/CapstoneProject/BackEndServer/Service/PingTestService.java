package CapstoneProject.BackEndServer.Service;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
@NoArgsConstructor
public class PingTestService {
    public boolean isHostReachable(String hostIP){
        boolean isReachable;
        try {
            InetAddress inetAddress = InetAddress.getByName(hostIP);
            isReachable = inetAddress.isReachable(5000);
            if(!isReachable) System.out.println("isUnReachable to " + hostIP);
            else System.out.println("isReachable to " + hostIP);
        }
        catch (IOException e){
            isReachable = false;
            System.out.println("isUnReachable to " + hostIP);
        }
        return isReachable;
    }
}