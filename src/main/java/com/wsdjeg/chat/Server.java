package com.wsdjeg.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.wsdjeg.chat.server.Account;
import com.wsdjeg.chat.server.Message;
import com.wsdjeg.chat.server.MessageThread;
import com.wsdjeg.chat.server.ServerThread;

public class Server extends ServerSocket {
    private static final int SERVER_PORT = 2013;

    public static void main (String[] args) throws IOException{
        int port = 0;
        if (port > 1023 && args.length > 0) {
            port = Integer.valueOf(args[0]);
            new Server(port);
        }else{
            new Server();
        }
    }

    public Server() throws IOException {
        super(SERVER_PORT);
        try {
            while (true) {
                Socket socket = accept();
                new ServerThread(socket);
                new MessageThread(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Server(int port) throws IOException {
        super(port);
        try {
            while (true) {
                Socket socket = accept();
                new ServerThread(socket);
                new MessageThread(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
