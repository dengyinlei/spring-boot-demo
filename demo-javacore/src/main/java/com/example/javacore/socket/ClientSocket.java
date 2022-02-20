package com.example.javacore.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 　　客户端通过ip和端口，连接到指定的server，然后通过Socket获得输出流，
 * 并向其输出内容，服务器会获得消息。最终服务端控制台打印如下：
 * 　通过这个例子应该掌握并了解：
 *
 * Socket服务端和客户端的基本编程
 * 传输编码统一指定，防止乱码
 */
public class ClientSocket {
    public static void main(String[] args) throws IOException {
        // 要连接的服务端IP地址和端口
        String host = "127.0.0.1";
        int port = 12345;
        // 与服务端建立连接
        /**
         * 底层会调用 socket.connect(new InetSocketAddress(host, port));
         */
        Socket socket = new Socket(host,12345);

        /**
         * 如何告知对方已发送完命令
         * 其实这个问题还是比较重要的，正常来说，客户端打开一个输出流，如果不做约定，也不关闭它，
         * 那么服务端永远不知道客户端是否发送完消息，那么服务端会一直等待下去，直到读取超时。所以怎么告知服务端已经发送完消息就显得特别重要。
         */

        // 建立连接后获得输出流
        OutputStream outputStream = socket.getOutputStream();
        String message="你好  yiwangzhibujian";
        socket.getOutputStream().write(message.getBytes("UTF-8"));
        //通过shutdownOutput告诉服务器已经发送完数据，后续只能接受数据

        //调用Socket的shutdownOutput()方法，底层会告知服务端我这边已经写完了，那么服务端收到消息后，就能知道已经读取完消息，
        // 如果服务端有要返回给客户的消息那么就可以通过服务端的输出流发送给客户端，如果没有，直接关闭Socket。
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        //只有当服务端关闭它的输出流的时候，服务端才能取得结尾的-1
        while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len,"UTF-8"));
        }
        System.out.println("get message from server: " + sb);




        inputStream.close();
        //　　如果关闭了输出流，那么相应的Socket也将关闭，和直接关闭Socket一个性质。
        outputStream.close();
        socket.close();
    }
}
