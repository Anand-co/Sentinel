package com.sentinel.controller;
import com.sentinel.dto.*;
import com.sentinel.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AuthService service;public AuthController(AuthService s){service=s;}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
 public Map<String,Object> register(@Valid @RequestBody RegisterRequest r,HttpServletRequest req){try{return service.register(r,req.getRemoteAddr());}catch(IllegalArgumentException e){throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());}}
 @PostMapping("/login")
 public Map<String,Object> login(@Valid @RequestBody LoginRequest r,HttpServletRequest req){try{return service.login(r,req.getRemoteAddr());}catch(IllegalArgumentException e){throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());}}
}
