package com.example.market_api_server_netty.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserService {
    public String proxyRegister(Map<String, String> params) {
        // TODO: 프록시 회원가입 로직 구현
        return "proxyRegisterResult%success";
    }
    public String userRegister(Map<String, String> params) {
        // TODO: 유저서버 회원가입 로직 구현
        return "userRegisterResult%success";
    }
} 