package com.example.market_api_server_netty.service;
import org.springframework.stereotype.Service;
import java.util.Map;
///123123
@Service
public class ProductService {
    public String searchProducts(Map<String, String> params, String token) {
        // 실제 DB 연동 전 임시 로직
        System.out.println("SearchProducts() 호출 됨");
        System.out.println("params : " + params);
        System.out.println("token " + token);

        //파라미터 예시 : name(검색어), page(페이지 번호)
        String keyword = params.get("name");
        String pageStr = params.get("page");
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;



        //아래는 실제 DB없이 가짜 데이터 반환 예시
        String mockData;
        if(keyword == null || keyword.isBlank()) {
            //검색어가 없으면 최신 상품 8개
            mockData = "101,나이키 후드티,images/nike.jpg,89000$102,아디다스 후드티," +
                    "images/adidas.jpg,85000$103,무신사 후드티,images/musinsa.jpg," +
                    "45000$104,유니클로 맨투맨,images/uniqlo.jpg,39000$105,탑텐 맨투맨," +
                    "images/topten.jpg,35000$106,폴로 셔츠,images/polo.jpg,99000$107," +
                    "노스페이스 패딩,images/north.jpg,250000$108,스파오 티셔츠," +
                    "images/spao.jpg,19000";
            System.out.println("=== 최신 상품 8개 반환 완료 ===");
        } else{
            //검색어가 있으면 해당 키워드 포함 상품만
            if("후드티".equals(keyword)){
                mockData = "201,나이키 후드티,images/nike.jpg,89000$202,무신사 후드티,images/musinsa.jpg,45000";
            } else {
                mockData = ""; // 검색결과 없을 시
                System.out.println("'" + keyword + "' 키워드 상품 없음");
            }
        }
        return "OK : " + mockData;
    }
    //임시로 설정한 토큰 인증 임시 방식
    private boolean isValidToken(String token){
        // 실서비스에서는 실제 검증 로직으로 교체
        return true;
    }
}
//    public String searchProducts(Map<String, String> params, String token){
//        // 파라미터에서 필요한 값들을 꺼내는 과정
//        String keyword = params.get("name"); // 검색어 (없으면 null 처리)
//        String pageStr = params.get("page"); // 페이지 번호 (없으면 null 처리)
//        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1; // 없으면 1페이지
//
//        // 여기에는 토큰 유효성 검사 로직을 넣을 것!!@@@@@@@@@@@@@@@@@@@@@@@@
//
//        // 상황에 따른 분기 처리
//        if(keyword == null){ // 검색어가 없을때
//            System.out.println("최신 상품 목록" + page + "페이지를 조회합니다.");
//            // 여기에 DB에 접근해서 최신 상품 8개를 가져오는 로직 구현
//        } else{ //검색어가 있을때
//            System.out.println("'"+keyword+"' 키워드로"+page+"페이지를 검색합니다.");
//            // 여기에 DB에 접근해서 키워드가 포함된 상품 8개를 가져오는 로직 구현
//        }
//        // DB에서 가져온 데이터를 클라이언트가 알아볼 수 있는 문자열로 만들기
//        String mockData = "1,후드티,hoodie.jpg,50000$2,청바지,jeans.jpg,70000";
//        return "OK :" + mockData;
//    }
//}
//public String searchProducts(Map<String, String> params, String token) {
//    String keyword = params.get("name");
//    String pageStr = params.get("page");
//    int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
//    int pageSize = 8;
//    int offset = (page - 1) * pageSize;
// 여기에는 토큰 유효성 검사 로직을 넣을 것!!@@@@@@@@@@@@@@@@@@@@@@@@
//
//    List<Product> productList;
//    if (keyword == null || keyword.isBlank()) {
//        // 전체 상품 최신순 8개
//        productList = productRepository.findLatestProducts(offset, pageSize);
//    } else {
//        // 검색어 포함 상품 최신순 8개
//        productList = productRepository.findProductsByName(keyword, offset, pageSize);
//    }
//
//    // 상품 목록을 원하는 문자열로 변환
//    StringBuilder sb = new StringBuilder("OK:");
//    for (Product p : productList) {
//        sb.append(p.getId()).append(",")
//                .append(p.getName()).append(",")
//                .append(p.getImageUrl()).append(",")
//                .append(p.getPrice()).append("$");
//    }
//    if (sb.charAt(sb.length() - 1) == '$') {
//        sb.deleteCharAt(sb.length() - 1); // 마지막 $ 제거
//    }
//    return sb.toString();
//}

