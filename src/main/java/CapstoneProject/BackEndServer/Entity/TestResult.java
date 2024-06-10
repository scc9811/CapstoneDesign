package CapstoneProject.BackEndServer.Entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    private BigDecimal averageTime;

    private int day;

    private int hour;

    private long userID;

}
