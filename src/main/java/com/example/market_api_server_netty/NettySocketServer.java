package com.example.market_api_server_netty;

import com.example.market_api_server_netty.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;


@Component
public class NettySocketServer {

    @Value("${netty.port:2020}")
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture serverChannelFuture;

    @PostConstruct
    public void start() throws InterruptedException {
        // 1. SSL 컨텍스트 생성 코드 추가
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // SSL/TLS 핸들러 추가 가능 (필요시)
                        // pipeline.addLast(sslCtx.newHandler(ch.alloc()));

                        // 필요한 디코더/인코더 추가 가능
                        // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

                        pipeline.addLast(new MyServerHandler()); // 실제 비즈니스 로직 핸들러
                    }
                });

        serverChannelFuture = bootstrap.bind(port).sync();
        System.out.println("Netty server started on port " + port);
    }

    @PreDestroy
    public void stop() {
        if (serverChannelFuture != null) {
            serverChannelFuture.channel().close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        System.out.println("Netty server stopped.");
    }
}