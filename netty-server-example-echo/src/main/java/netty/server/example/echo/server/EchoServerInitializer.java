package netty.server.example.echo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/28
 * @since Jdk 1.8
 */
public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192,
                Delimiters.lineDelimiter()));

        //添加编码解码类
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        //添加处理业务的类
        pipeline.addLast(new EchoServerHandler());

    }
}
