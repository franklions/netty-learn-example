package netty.server.example.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/28
 * @since Jdk 1.8
 */
public class EchoClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
    static final int SIZE =Integer.parseInt(System.getProperty("size","256"));

    public static void main(String[] args) throws SSLException {
        final SslContext sslCtx;
        if(SSL){
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }else{
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                            .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                                       ChannelPipeline p = ch.pipeline();
                                          if (sslCtx != null) {
                                               p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                                           }
                                         //p.addLast(new LoggingHandler(LogLevel.INFO));
                                           p.addLast(new EchoClientHandler());
                                     }
               });

                          // Start the client.
            ChannelFuture f = bootstrap.connect(HOST, PORT).sync();
                        f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
