package com.sentinel.service;
import com.sentinel.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;
@Service
public class SecurityDashboardService {
 private final SecurityEventRepository events;
 public SecurityDashboardService(SecurityEventRepository e){events=e;}
 public Map<String,Object> summary(){Instant since=Instant.now().minus(24, java.time.temporal.ChronoUnit.HOURS);Map<String,Object> m=new LinkedHashMap<>();
  m.put("failedLogins24h",events.countFailedSince(since));m.put("successfulLogins24h",events.countByEventTypeAndOccurredAtAfter("LOGIN_SUCCESS",since));
  m.put("highSeverity24h",events.countBySeverityAndOccurredAtAfter("HIGH",since));m.put("status","monitoring");return m;}
}
