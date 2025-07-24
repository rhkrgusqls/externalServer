package com.example.market_api_server_netty.handler;

import com.example.market_api_server_netty.service.MarketDataService;
import com.example.market_api_server_netty.service.ProductService;
import com.example.market_api_server_netty.service.AuthService;
import com.example.market_api_server_netty.service.TokenService;
import com.example.market_api_server_netty.util.BeanUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.HashMap;
import java.util.Map;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf in = (ByteBuf) msg;
//        String fullCommand = in.toString(CharsetUtil.UTF_8);
        String fullCommand = (String) msg;
        System.out.println("사용자에게로 부터 받은 메시지 : " + fullCommand);

        String responseMessage; // 최종 응답 메시지를 담을 변수

        try {
            // 1. 명령어 파싱
            String[] parts = fullCommand.split("%");
            if (parts.length < 2) {
                throw new IllegalArgumentException("유효하지 않는 포맷 형식 입니다.");
            }
            String commandCode = parts[0];
            String dataPart = parts[1];
            String token = (parts.length > 2) ? parts[2] : null;

            Map<String, String> dataMap = new HashMap<>();
            if(!dataPart.isBlank()){
                String[] dataSegments = dataPart.split("\\$");
                for(String segment : dataSegments){
                    String[] keyValue = segment.split("&");
                    if(keyValue.length == 2){
                        dataMap.put(keyValue[0], keyValue[1]);
                    }
                }
            }

            // 2. 파싱된 명령에 따라 실제 비즈니스 로직(서비스) 호출

            switch (commandCode) {
                case "GET_ACCESS_TOKEN": // userId를 받아서 임시 토큰을 발급
                    MarketDataService accessService = BeanUtil.getBean(MarketDataService.class);
                    responseMessage = accessService.getAccessToken(dataMap);
                    break;
                case "PRODUCT_SEARCH":
                    ProductService productService = BeanUtil.getBean(ProductService.class);
                    responseMessage = productService.searchProducts(dataMap, token);
                    break;
                case "SearchData":
                    ProductService detailService = BeanUtil.getBean(ProductService.class);
                    responseMessage = detailService.getProductDetails(dataMap, token);
                    break;
                case "LOGIN":
                    AuthService authService = BeanUtil.getBean(AuthService.class);
                    boolean loginResult = authService.authenticate(dataMap);
                    responseMessage = "loginResult%" + (loginResult ? "success" : "fail");
                    break;
                case "SAVE_DATA":
                    ProductService saveService = BeanUtil.getBean(ProductService.class);
                    responseMessage = saveService.saveData(dataMap, token);
                    break;
                case "GET_PUBLIC_KEY":
                    TokenService tokenService = BeanUtil.getBean(TokenService.class);
                    responseMessage = "publicKey%" + tokenService.getPublicKey();
                    break;
                default:
                    responseMessage = "Error : 알수 없는 명령어 입니다. : "+ commandCode;
            }
        } catch (Exception e) {
            responseMessage = "Error: " + e.getMessage(); // 에러 발생 시 에러 메시지를 응답으로 설정
        }
        // 3. 모든 로직이 끝난 후, 최종 결과물을 클라이언트로 전송
        ctx.writeAndFlush(Unpooled.copiedBuffer(responseMessage + "\n", CharsetUtil.UTF_8));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("Client disconnected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}