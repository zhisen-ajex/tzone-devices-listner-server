package com.ajex.temperatureserver.netty;


import com.ajex.temperatureserver.config.netty.WebSocketConfiguration;
import com.ajex.temperatureserver.exception.WebSocketException;
import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketServer {
    @Autowired
    private final WebSocketConfiguration webSocketConfiguration;



    public void start() {
        if (webSocketConfiguration.isEnable()) {
            EventLoopGroup bossGroup, workerGroup;
            ServerBootstrap server = new ServerBootstrap();
            log.info("webSocketConfiguration:{}", JSON.toJSONString(webSocketConfiguration));
            int bossThreads = webSocketConfiguration.getBossThreads();
            int workerThreads = webSocketConfiguration.getWorkerThreads();
            boolean epoll = webSocketConfiguration.isEpoll();

            if (epoll) {
                bossGroup = new EpollEventLoopGroup(bossThreads,
                        new DefaultThreadFactory("WebSocketBossGroup", true));
                workerGroup = new EpollEventLoopGroup(workerThreads,
                        new DefaultThreadFactory("WebSocketWorkerGroup", true));
                server.channel(EpollServerSocketChannel.class);
            } else {
                bossGroup = new NioEventLoopGroup(bossThreads);
                workerGroup = new NioEventLoopGroup(workerThreads);
                server.channel(NioServerSocketChannel.class);
            }

            server.group(bossGroup, workerGroup)
                    .childHandler(new WSServerInitialize())
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            log.info("WebSocketServer - Starting...");
            //绑定端口,开始接收进来的连接
            try {
                ChannelFuture future = server.bind(webSocketConfiguration.getPort()).sync();
                future.addListener(f -> {
                    if (f.isSuccess()) {
                        log.info("WebSocketServer - Start completed.");
                    } else {
                        throw new WebSocketException("WebSocketServer - Start failed.");
                    }
                });
                log.info("The server starts to listen on the port: {}", webSocketConfiguration.getPort());
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("Server start failed:{}", e);
            } finally {
                //关闭主线程组
                bossGroup.shutdownGracefully();
                //关闭工作线程组
                workerGroup.shutdownGracefully();
            }
        }else{
            log.info("netty.websocket.enable=false，不开启websocket服务");
        }

    }
}