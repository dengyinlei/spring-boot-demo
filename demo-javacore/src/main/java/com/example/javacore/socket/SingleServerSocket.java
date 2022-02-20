package com.example.javacore.socket;


import java.io.InputStream;
import java.net.Socket;

public class SingleServerSocket implements Runnable{

    private Socket socket;
    private int clientNo;

    public SingleServerSocket(Socket socket, int clientNo) {
        this.socket = socket;
        this.clientNo = clientNo;
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes) )!= -1) {
                sb.append(new String(bytes, 0, len, "utf-8"));
            }
            System.out.println("get message from client: " + sb);
            inputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }

    }
}
