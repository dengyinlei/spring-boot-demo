package com.example.javacore.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 　　服务端监听一个端口，等待连接的到来。
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        //1、创建ServerSocket对象，绑定监听的端口
        //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        /** 等价于
         * ServerSocket serverSocket = new ServerSocket();
         * serverSocket.bind(new InetSocketAddress(12345));
         */
        ServerSocket serverSocket = new ServerSocket(12345);
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        //2.调用accept()一直阻塞等待客户端连接
        Socket socket = serverSocket.accept();
        System.out.println("~~~服务端已就绪，等待客户端接入~，服务端ip地址:" + ip);
        // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
        while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len,"UTF-8"));
        }
        System.out.println("get message from client: " + sb);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("Hello Client,I get the message.".getBytes("UTF-8"));
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();

    }
}
