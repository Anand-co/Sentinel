package com.sentinel.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="app_users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=80) private String name;
 @Column(nullable=false,unique=true,length=180) private String email;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false) private int failedLoginCount=0;
 private Instant lockedUntil;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected AppUser(){}
 public AppUser(String name,String email,String passwordHash){this.name=name;this.email=email;this.passwordHash=passwordHash;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
 public String getPasswordHash(){return passwordHash;} public int getFailedLoginCount(){return failedLoginCount;}
 public Instant getLockedUntil(){return lockedUntil;} public Instant getCreatedAt(){return createdAt;}
 public void setFailedLoginCount(int v){failedLoginCount=v;} public void setLockedUntil(Instant v){lockedUntil=v;}
}
