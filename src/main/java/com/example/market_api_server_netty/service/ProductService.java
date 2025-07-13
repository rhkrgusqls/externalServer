package com.example.market_api_server_netty.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ProductService {

    private final com.example.market_api_server_netty.service.TokenService tokenService;

    @Autowired
    public ProductService(com.example.market_api_server_netty.service.TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String searchProducts(Map<String, String> params, String token) {
        System.out.println("SearchProducts() 호출 됨");
        System.out.println("params : " + params);
        System.out.println("token " + token);

        // TODO: 상품 검색은 로그인이 필수인 기능인지 정책 결정이 필요
        //  만약 로그인이 필수라면 아래 토큰 검증 로직을 사용하고,
        //  선택적이라면 토큰이 있을 때만 검증하고 사용자별 추천 로직 등을 추가할 수 있습니다.
        //  여기서는 토큰이 필수라고 가정하고 구현합니다.
        try {
            if (token == null || token.isBlank()) {
                return "Error:Missing token";
            }
            Claims claims = tokenService.validateAndGetClaims(token);
            System.out.println("Token validation successful for user: " + claims.getSubject());
        } catch (IllegalArgumentException e) {
            return "Error:" + e.getMessage();
        }

        String keyword = params.get("name");
        String pageStr = params.get("page");
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

        // TODO: 이 아래는 실제 DB와 연동하여 데이터를 조회하는 로직으로 대체되어야 합니다.
        //  JPA Repository 등을 사용하여 keyword와 page 정보를 바탕으로 데이터를 조회합니다.
        String mockData;
        if(keyword == null || keyword.isBlank()) {
            mockData = "101,나이키 후드티,images/nike.jpg,89000$102,아디다스 후드티,images/adidas.jpg,85000$103,무신사 후드티,images/musinsa.jpg,45000";
            System.out.println("=== 최신 상품 목록 반환 완료 ===");
        } else {
            if("후드티".equals(keyword)){
                mockData = "201,나이키 후드티,images/nike.jpg,89000$202,무신사 후드티,images/musinsa.jpg,45000";
            } else {
                mockData = ""; // 검색결과 없음
                System.out.println("'" + keyword + "' 키워드 상품 없음");
            }
        }
        return "OK:" + mockData; // 성공 시 "OK:" 접두사 사용
    }
}
