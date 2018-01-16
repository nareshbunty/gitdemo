package com.happybus.processing.service;

public interface UserAuthenticationService {
public boolean isAuthenticated(Long userId,String token);
}
