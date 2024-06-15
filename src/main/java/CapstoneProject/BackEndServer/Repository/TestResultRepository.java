package CapstoneProject.BackEndServer.Repository;

import CapstoneProject.BackEndServer.Entity.TestResult;
import CapstoneProject.BackEndServer.Entity.TestResultId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, TestResultId> {
    List<TestResult> findByIdUserId(Long userId);
}
