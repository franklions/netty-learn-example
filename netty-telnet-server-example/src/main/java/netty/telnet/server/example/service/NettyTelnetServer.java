package netty.telnet.server.example.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/20
 * @since Jdk 1.8
 */
public class NettyTelnetServer {

    private int port=8888;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public NettyTelnetServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();
        //指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG,1024);
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)  //指定一个NIO连接通道
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        //绑定对应的端口号，并启动开始监听端口上的连接
        Channel ch =serverBootstrap.bind(this.port).sync().channel();
        //等待关闭，同步端口
        ch.closeFuture().sync();
    }

    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
