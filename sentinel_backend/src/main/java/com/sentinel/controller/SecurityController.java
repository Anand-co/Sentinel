package com.sentinel.controller;
import com.sentinel.model.SecurityEvent;
import com.sentinel.repository.SecurityEventRepository;
import com.sentinel.service.SecurityDashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/security")
public class SecurityController {
 private final SecurityEventRepository events;private final SecurityDashboardService dashboard;
 public SecurityController(SecurityEventRepository e,SecurityDashboardService d){events=e;dashboard=d;}
 @GetMapping("/summary") public Map<String,Object> summary(Authentication a){return dashboard.summary();}
 @GetMapping("/events") public List<SecurityEvent> events(Authentication a){return events.findTop100ByOrderByOccurredAtDesc();}
 @GetMapping("/health") public Map<String,String> health(Authentication a){return Map.of("status","ok","authenticatedAs",a.getName());}
}
