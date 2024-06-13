package CapstoneProject.BackEndServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

// TestResult Table's PK
@Embeddable
@Data
@ToString
public class TestResultId implements Serializable {

    private int day;

    private int hour;

    @Column(name = "user_id")
    private Long userId;

}
