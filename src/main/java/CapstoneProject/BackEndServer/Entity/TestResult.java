package CapstoneProject.BackEndServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @EmbeddedId
    private TestResultId id;

    @Column(name = "average_time")
    private BigDecimal averageTime;

// 월 ~ 일 --> 1 ~ 7
//    private int day;
//
//    private int hour;
//
//    private long userId;

}
