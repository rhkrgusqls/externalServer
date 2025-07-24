package com.example.market_api_server_netty.service;

public class TestAuthService implements AuthService {
    @Override
    public boolean authenticate(String input) {
        return true;
    }

    @Override
    public boolean authenticate(java.util.Map<String, String> params) {
        return true;
    }
} 