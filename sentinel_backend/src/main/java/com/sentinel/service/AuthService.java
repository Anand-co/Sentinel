package com.sentinel.service;
import com.sentinel.dto.*;
import com.sentinel.model.*;
import com.sentinel.repository.*;
import com.sentinel.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;
@Service
public class AuthService {
 private final UserRepository users; private final SecurityEventRepository events; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthService(UserRepository u,SecurityEventRepository e,PasswordEncoder p,JwtService j){users=u;events=e;encoder=p;jwt=j;}
 @Transactional public Map<String,Object> register(RegisterRequest r,String ip){
  String email=r.email().trim().toLowerCase();if(users.existsByEmailIgnoreCase(email))throw new IllegalArgumentException("An account with this email already exists.");
  AppUser u=users.save(new AppUser(r.name().trim(),email,encoder.encode(r.password())));
  events.save(new SecurityEvent("ACCOUNT_CREATED",email,ipSafe(ip),"LOW","New account registered"));
  return Map.of("message","Account created successfully","name",u.getName(),"email",u.getEmail());
 }
 @Transactional public Map<String,Object> login(LoginRequest r,String ip){
  String email=r.email().trim().toLowerCase();AppUser u=users.findByEmailIgnoreCase(email).orElse(null);
  if(u==null){events.save(new SecurityEvent("LOGIN_FAILED",email,ipSafe(ip),"MEDIUM","Login attempt for an unknown account"));throw new IllegalArgumentException("Invalid email or password.");}
  Instant now=Instant.now();
  if(u.getLockedUntil()!=null&&u.getLockedUntil().isAfter(now)){events.save(new SecurityEvent("LOGIN_BLOCKED",email,ipSafe(ip),"HIGH","Login blocked because account is temporarily locked"));throw new IllegalArgumentException("Account locked for 15 minutes after repeated failures.");}
  if(!encoder.matches(r.password(),u.getPasswordHash())){
   int n=u.getFailedLoginCount()+1;u.setFailedLoginCount(n);boolean lock=n>=5;if(lock)u.setLockedUntil(now.plusSeconds(900));
   events.save(new SecurityEvent(lock?"BRUTE_FORCE_SUSPECTED":"LOGIN_FAILED",email,ipSafe(ip),lock?"HIGH":(n>=3?"MEDIUM":"LOW"),lock?"Repeated failed logins detected; account locked for 15 minutes":"Incorrect password (consecutive failure "+n+")"));
   throw new IllegalArgumentException("Invalid email or password."+(lock?" Too many attempts: account locked for 15 minutes.":""));
  }
  u.setFailedLoginCount(0);u.setLockedUntil(null);events.save(new SecurityEvent("LOGIN_SUCCESS",email,ipSafe(ip),"LOW","Successful login"));
  return Map.of("message","Login successful","token",jwt.generate(email),"name",u.getName(),"email",u.getEmail());
 }
 private String ipSafe(String ip){if(ip==null||ip.isBlank())return "unknown";return ip.length()>80?ip.substring(0,80):ip;}
}
