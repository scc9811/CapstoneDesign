package CapstoneProject.BackEndServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// TestResult Table's PK
@Embeddable
@Data
public class TestResultId implements Serializable {

    private int day;

    private int hour;

    @Column(name = "user_id")
    private Long userId;

}
