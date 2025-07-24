package com.example.market_api_server_netty.controller;

import org.springframework.web.bind.annotation.*;
import com.example.market_api_server_netty.service.AuthService;
import com.example.market_api_server_netty.service.TestAuthService;

@RestController
@RequestMapping("/api")
public class ServerController {

    private final AuthService authService;

    public ServerController() {
        this.authService = new TestAuthService();
    }

    // 1. DB 데이터 호출 (테이블별 전체 데이터 문자열 반환)
    @GetMapping("/fetch/{table}")
    public String fetchTableData(@PathVariable String table) {
        // 예시: "item%1%카테고리1%상품A|2%카테고리2%상품B"
        return "item%1%카테고리1%상품A|2%카테고리2%상품B";
    }

    // 2. 로그인 판단 및 처리
    @PostMapping("/login")
    public String login(@RequestBody String body) {
        // body 예시: "id%pw"
        boolean result = authService.authenticate(body);
        return "loginResult%" + (result ? "success" : "fail");
    }

    // 3. 토큰 생성 및 전송
    @PostMapping("/token")
    public String createToken(@RequestBody String body) {
        // body 예시: "id"
        return "token%생성된토큰값";
    }

    // 4. 토큰 공개키 전송
    @GetMapping("/token/publickey")
    public String getTokenPublicKey() {
        return "publicKey%공개키값";
    }

    // 5. DB 저장자 (성공/실패 반환)
    @PostMapping("/save/{table}")
    public String saveData(@PathVariable String table, @RequestBody String body) {
        // body 예시: "item%1%카테고리1%상품A"
        boolean success = true; // 가데이터 처리
        return "saveResult%" + (success ? "success" : "fail");
    }
} 