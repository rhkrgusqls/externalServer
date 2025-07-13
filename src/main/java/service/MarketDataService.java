package com.example.market_api_server_netty.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MarketDataService {
    private final com.example.market_api_server_netty.service.TokenService tokenService;

    @Autowired
    public MarketDataService(com.example.market_api_server_netty.service.TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String getMarketData(Map<String, String> params, String token) {
        System.out.println("Executing getMarketData with params: " + params + " token: " + token);

        // TODO : 토큰 검증 실패시, 종류에 따라 다른 에러 코드를 반환하는 것도 좋다고 함
        try{
            if(token == null || token.isBlank()){
                return "Error:Missing token"; // 토큰이 null일시 출력
            }
            // TokenService를 사용하여 토큰 검증
            Claims claims = tokenService.validateAndGetClaims(token);
            String userIdFromToken = claims.getSubject();
            System.out.println("접속 유저에 대한 토큰 인증이 성공적으로 마쳤습니다." + userIdFromToken);
        } catch(IllegalArgumentException e){
            //validateAndGetClaims에서 발생한 모든 예외(만료, 형식 오루 등) 처리
            return "Error: " + e.getMessage();
        }
        String item = params.get("item");
        if(item == null || item.isBlank()){
            return "Error:item을 찾지 못했습니다.";
        }
        // TODO: 이 부분에서 DB에 접근하여 실제 시장 데이터를 조회해야함.
        String mockMarketData = "Market data for " + item + " is 10000 USD.";
        return "OK:" + mockMarketData;
    }

    public String getAccessToken(Map<String, String> params) {
        System.out.println("Executing getAccessToken with params: " + params);
        String userId = params.get("userId");
        if(userId == null){
            return "Error:'userId' data is missing";
        }

        // TODO: 이 부분에서 DB를 조회하여 userId가 실제로 존재하는 사용자인지 확인하는 로직이 필요합니다.
        // 사용자 존재 확인 후 토큰 발급
        String accessToken = tokenService.generateAccessToken(userId);
        System.out.println("Generated new access token for user " + userId);
        return "OK:" + accessToken; // 성공 시 "OK:" 접두사와 함께 토큰 반환
    }
}
