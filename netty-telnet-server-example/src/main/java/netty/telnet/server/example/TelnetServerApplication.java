package netty.telnet.server.example;

import netty.telnet.server.example.service.NettyTelnetServer;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/20
 * @since Jdk 1.8
 */
public class TelnetServerApplication {
    public static void main(String[] args) {
        NettyTelnetServer telnetServer = new NettyTelnetServer(8888);
        try {
            telnetServer.start();
        } catch (InterruptedException e) {
            telnetServer.close();
        }
    }
}
