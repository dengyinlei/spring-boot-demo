package com.example.javacore.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 */
public class MutiThreadSocketServer {
    public static void main(String[] args) throws IOException {
        int clientNo = 1;
        ServerSocket serverSocket = new ServerSocket(12345);
        ExecutorService threadExectorService = Executors.newFixedThreadPool(100);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                threadExectorService.submit(new SingleServerSocket(socket, clientNo));
                clientNo ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            serverSocket.close();
        }

    }
}
