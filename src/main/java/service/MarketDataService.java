package com.example.market_api_server_netty.service;


import org.springframework.stereotype.Service;
import java.util.Map;


/**
 * 시장 데이터를 조회하는 메소드
 * /@param params 클라이언트가 보낸 데이터 (예: item="BTC", count="10")
 * /@param token 인증 토큰
 * /@return 클라이언트에게 보낼 응답 문자열
 */
@Service
public class MarketDataService {
    public String getMarketData(Map<String, String> params, String token) {
        System.out.println("Executing getMarketData with params: " + params + " and token: " + token);
        if(token == null || !isValidToken(token)){
            return "Error : Invalid or missing token";
        }
        String item = params.get("item");
        if(item == null){
            return "Error : 'item' data is missing";
        }
        //실제 데이터 조회 로직(지금은 임시데이터 반환)
        return "Market data for " + item + " is 10000 USD.";
    }
    /**
     * 접근 토큰을 발급하는 메소드
     * /@param params 클라이언트가 보낸 데이터 (예: userId="testUser")
     * /@return 클라이언트에게 보낼 새로운 토큰 문자열
     */
    public String getAccessToken(Map<String, String> params) {
        System.out.println("Executing getAccessToken with params: " + params);
        String userId = params.get("userId");
        if(userId == null){
            return "Error: 'userId' data is missing";
        }
        //실제 토큰 생성 로직(지금은 임시토큰 반환)
        return "NewAccessToken-XYZ12345";
    }
    //토큰 유효성 검사 메소드,(임시)
    private boolean isValidToken(String token){
        //검증 로직
        return token.startsWith("Valid-");
    }
}
