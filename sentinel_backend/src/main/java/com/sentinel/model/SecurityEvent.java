package com.sentinel.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="security_events")
public class SecurityEvent {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=40) private String eventType;
 @Column(nullable=false,length=180) private String email;
 @Column(nullable=false,length=80) private String sourceIp;
 @Column(nullable=false,length=20) private String severity;
 @Column(nullable=false,length=500) private String details;
 @Column(nullable=false) private Instant occurredAt=Instant.now();
 protected SecurityEvent(){}
 public SecurityEvent(String type,String email,String ip,String severity,String details){this.eventType=type;this.email=email;this.sourceIp=ip;this.severity=severity;this.details=details;this.occurredAt=Instant.now();}
 public Long getId(){return id;} public String getEventType(){return eventType;} public String getEmail(){return email;}
 public String getSourceIp(){return sourceIp;} public String getSeverity(){return severity;} public String getDetails(){return details;}
 public Instant getOccurredAt(){return occurredAt;}
}
