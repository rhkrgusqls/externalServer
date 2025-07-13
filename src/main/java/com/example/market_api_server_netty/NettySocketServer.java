package com.example.market_api_server_netty;

import com.example.market_api_server_netty.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;


@Component
public class NettySocketServer {

    @Value("${netty.port:2020}")
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture serverChannelFuture;

    @PostConstruct
    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        // ServerBootstrap 객체 생성
        ServerBootstrap bootstrap = new ServerBootstrap();

        // bootstrap에 모든 설정을 점(.)으로 연결하여 연쇄적으로 호출.
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();

                        // 클라이언트가 보내는 메시지를 처리하기 위한 디코더 추가
                        pipeline.addLast(new LineBasedFrameDecoder(8192));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));

                        // 실제 비즈니스 로직 핸들러
                        pipeline.addLast(new MyServerHandler());
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