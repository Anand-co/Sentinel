package com.sentinel.repository;
import com.sentinel.model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.Instant;
import java.util.List;
public interface SecurityEventRepository extends JpaRepository<SecurityEvent,Long>{
 List<SecurityEvent> findTop100ByOrderByOccurredAtDesc();
 long countByEventTypeAndOccurredAtAfter(String type,Instant after);
 long countBySeverityAndOccurredAtAfter(String severity,Instant after);
 @Query("select count(e) from SecurityEvent e where e.eventType='LOGIN_FAILED' and e.occurredAt >= :after")
 long countFailedSince(Instant after);
}
