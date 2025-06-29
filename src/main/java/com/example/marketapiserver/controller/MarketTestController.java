package com.example.marketapiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

// 이 클래스가 REST API 요청을 처리하는 컨트롤러임을 Spring에게 알림
@RestController
// '/api/market' 으로 시작하는 모든 URL 요청은 이 컨트롤러로 오게 함
@RequestMapping("/api/market")
public class MarketTestController {

    // HTTP GET 메소드로 '/api/market/test' 주소에 요청이 오면 이 메소드를 실행
    @GetMapping("/test")
    public String handleTestRequest(@RequestParam String clientMessage) {
        System.out.println("LOG : 클라이언트로부터 문자열 수신 요청이 들어왔습니다.");
        System.out.println("서버가 받은 메시지 : " + clientMessage);
        return "서버 응답 : 당신의 메시지 '" + clientMessage + "'를 성공적으로 받았습니다.";

    }
}